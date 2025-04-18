package com.example.maple.service;

import com.example.maple.dto.OcidResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RankerUnionChampionPopulationServiceTest {

    @Mock
    private OcidService ocidService;

    @InjectMocks
    private RankerUnionChampionPopulationService rankerUnionChampionPopulationService;

    @Test
    void getOcidListWithRateLimit_shouldReturnValidResponse() {
        // given
        List<String> characterNames = List.of("나이트로드", "나이트워커", "다크나이트");

        OcidResponse response1 = new OcidResponse();
        OcidResponse response2 = new OcidResponse();
        OcidResponse response3 = new OcidResponse();

        response1.setOcid("ocid1");
        response2.setOcid("ocid2");
        response3.setOcid("ocid3");

        when(ocidService.getOcid("나이트로드")).thenReturn(response1);
        when(ocidService.getOcid("나이트워커")).thenReturn(response2);
        when(ocidService.getOcid("다크나이트")).thenReturn(response3);

        // when
        List<OcidResponse> result = rankerUnionChampionPopulationService.getOcidListWithRateLimit(characterNames);

        // then
        assertEquals(3, result.size());
        assertTrue(result.stream().anyMatch(r -> "ocid1".equals(r.getOcid())));
        assertTrue(result.stream().anyMatch(r -> "ocid2".equals(r.getOcid())));
        assertTrue(result.stream().anyMatch(r -> "ocid3".equals(r.getOcid())));
    }

    @Test
    void getOcidListWithRateLimit_shouldSkipFailedRequests() {
        // Given
        List<String> characterNames = List.of("나이트로드", "나이트워커", "에러남");

        OcidResponse response1 = new OcidResponse();
        OcidResponse response2 = new OcidResponse();

        response1.setOcid("ocid1");
        response2.setOcid("ocid2");

        when(ocidService.getOcid("나이트로드")).thenReturn(response1);
        when(ocidService.getOcid("나이트워커")).thenReturn(response2);
        when(ocidService.getOcid("에러남")).thenThrow(new RuntimeException("API 에러"));

        // When
        List<OcidResponse> result = rankerUnionChampionPopulationService.getOcidListWithRateLimit(characterNames);

        // Then
        assertEquals(2, result.size());
        assertFalse(result.stream().anyMatch(r -> r.getOcid().equals("에러남")));
    }
}