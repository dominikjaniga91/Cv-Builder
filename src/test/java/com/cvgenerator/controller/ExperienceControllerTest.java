package com.cvgenerator.controller;

import com.cvgenerator.domain.entity.Experience;
import com.cvgenerator.domain.entity.Interest;
import com.cvgenerator.service.implementation.ExperienceServiceImpl;
import com.cvgenerator.service.implementation.InterestServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WithMockUser(username = "admin", password = "admin")
@DisplayName("Request to experience controller using http method")
public class ExperienceControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private ExperienceServiceImpl experienceService;
    private String token;
    private Experience experience;

    @BeforeEach
    void setUp() {

        token = Jwts
                .builder()
                .claim("role", "USER")
                .setSubject("admin")
                .setExpiration(new Date(System.currentTimeMillis() + 86_400_000))
                .signWith(SignatureAlgorithm.HS256, "6fg28#h$h*uiq2con!%^&k5k()_mrj8")
                .compact();

        experience = Experience.builder()
        .company("Jeronimo Martins")
        .city("Tarnów")
        .position("Inspector")
        .startDate(LocalDate.now())
        .finishDate(LocalDate.now())
        .description("Great jod")
        .build();
    }

    @Test
    @DisplayName("POST should return status 'created' after post Experience")
    void shouldReturnStatusCreated_afterPostExperience() throws Exception {

        BDDMockito.doNothing().when(experienceService).createExperience(anyLong(), any(Experience.class));

        mockMvc.perform(post("/api/cv/experience/{cvId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(experience))
                .header("Authorization", "Bearer " + token)
                .header("Access-Control-Expose-Headers", "Authorization"))
                .andExpect(status().isCreated())
                .andDo(print());

        BDDMockito.verify(experienceService, Mockito.times(1)).createExperience(anyLong(), any(Experience.class));
        BDDMockito.verifyNoMoreInteractions(experienceService);
    }

    @Test
    @DisplayName("PUT should return status 'ok' after update Experience")
    void shouldReturnStatusOK_afterUpdateExperience() throws Exception {

        BDDMockito.doNothing().when(experienceService).updateExperience(ArgumentMatchers.any(Experience.class));

        mockMvc.perform(put("/api/cv/experience")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(experience))
                .header("Authorization", "Bearer " + token)
                .header("Access-Control-Expose-Headers", "Authorization"))
                .andExpect(status().isOk())
                .andDo(print());

        BDDMockito.verify(experienceService, Mockito.times(1)).updateExperience(ArgumentMatchers.any(Experience.class));
        BDDMockito.verifyNoMoreInteractions(experienceService);
    }

    @Test
    @DisplayName("DELETE should return status 'ok' after delete Experience")
    void shouldReturnStatusOK_afterDeleteExperience() throws Exception {

        BDDMockito.doNothing().when(experienceService).deleteExperienceById(1L);

        mockMvc.perform(delete("/api/cv/experience/{id}", 1L)
                .header("Authorization", "Bearer " + token)
                .header("Access-Control-Expose-Headers", "Authorization"))
                .andExpect(status().isOk())
                .andDo(print());

        BDDMockito.verify(experienceService, Mockito.times(1)).deleteExperienceById(1L);
        BDDMockito.verifyNoMoreInteractions(experienceService);
    }

}
