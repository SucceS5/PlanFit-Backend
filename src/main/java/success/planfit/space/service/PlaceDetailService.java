package success.planfit.space.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import success.planfit.entity.space.SpaceDetail;
import success.planfit.repository.CachePlaceDetailRepository;
import success.planfit.repository.UserRepository;
import success.planfit.space.dto.PlaceDetailMappingDto;
import success.planfit.space.dto.request.CachePlaceDetailSaveRequestDto;
import success.planfit.space.dto.request.PlaceDetailRequestDto;
import success.planfit.space.dto.request.PlaceRelevanceDetail;
import success.planfit.space.dto.response.LocationDetailResponseDto;
import success.planfit.space.dto.response.PlaceDetailResponseDto;
import success.planfit.user.dto.UserUpdateDto;

import java.util.Optional;

@AllArgsConstructor
@Service
public class PlaceDetailService {
    private OpenGooglePlaceApi openGooglePlaceApi;
    private UserRepository userRepository;
    private CachePlaceDetailRepository cachePlacedetailRepository;

    // AI에게 좌표값과 유저 정보 전달
    public LocationDetailResponseDto passPlaceDetail(Long id, PlaceDetailRequestDto placeDetailRequestDto){
        return LocationDetailResponseDto.builder()
                .userUpdateDto(UserUpdateDto.from(userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID를 통해 유저 조회 실패"))))
                .placeDetailRequestDto(placeDetailRequestDto)
                .build();
    }


    // AI에게 받은 값들로 API 조회후 정보값 전달
    public PlaceDetailResponseDto getPlaceDetailsById(PlaceRelevanceDetail requestDto){
        String placeId = requestDto.getPlaceId();

        // AI가 준 값들 중 type과 적합도 처리 로직 구현 필요

        Optional<SpaceDetail> place = cachePlacedetailRepository.findByGooglePlacesIdentifier(placeId);

        //만약에 캐시 테이블에 정보 있다면 해당 정보 테이블에서 찾아서 dto로 리턴
        if (place.isPresent()) {
            return PlaceDetailResponseDto.createFromCache(place.orElseThrow(()-> new IllegalArgumentException()));
        } // 만약에 캐시 테이블에 정보 없다면 API로 조회후 dto로 리턴

        String placeDetailJson = openGooglePlaceApi.fetchPlaceDetailsByplaceId(placeId);

        // json String SpaceBook 객체에 넣기
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        PlaceDetailMappingDto responseDto;

        try {
            // json -> dto에 맵핑
            responseDto = objectMapper.readValue(placeDetailJson,
                    new TypeReference<PlaceDetailMappingDto>(){});
            // mappingDto -> savedto
            CachePlaceDetailSaveRequestDto saveRequestDto = CachePlaceDetailSaveRequestDto.createSaveDtoFromMapper(responseDto);
            // 캐시메모리에 저장
            cachePlacedetailRepository.save(saveRequestDto.toEntity());

            // responseDto 리턴
            return PlaceDetailResponseDto.createFromMapper(responseDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }









}
