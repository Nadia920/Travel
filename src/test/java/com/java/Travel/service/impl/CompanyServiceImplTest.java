package com.java.Travel.service.impl;


import com.java.Travel.controller.dto.CompanyDTO;
import com.java.Travel.model.CompanyEntity;
import com.java.Travel.model.Rating;
import com.java.Travel.repository.CompanyEntityRepository;
import com.java.Travel.service.ServiceImpl.CompanyServiceImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.*;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CompanyServiceImplTest {

    @Mock
    private CompanyEntityRepository companyRepository;

    @Mock
    private Pageable pageable;

    @InjectMocks
    private CompanyServiceImpl companyService;

    @Test
    public void save() {
        companyService.save(new CompanyDTO("Fake", Rating.FIVE_STARS));
        verify(companyRepository).save(isA(CompanyEntity.class));
    }

    @Test
    public void findAll() {
        List<CompanyEntity> list = new ArrayList<>();
        CompanyEntity companyEntity1 = new CompanyEntity();
        CompanyEntity companyEntity2 = new CompanyEntity();
        list.add(companyEntity1);
        list.add(companyEntity2);

        when(companyRepository.findAllAndOrderByName()).thenReturn(list);

        List<CompanyDTO> empList = companyService.findAll();

        assertEquals(2, empList.size());
        verify(companyRepository, times(1)).findAllAndOrderByName();
    }

    @Test
    public void findAllPageable() {
        pageable = PageRequest.of(0, 4, Sort.by("name").ascending());
        Page<CompanyEntity> companyEntities = new PageImpl<>(Arrays.asList(
                new CompanyEntity(1L, "Fake1"),
                new CompanyEntity(2L, "Fake2"),
                new CompanyEntity(3L, "Fake3"),
                new CompanyEntity(5L, "Fake7")));

        when(companyRepository.findAll(pageable)).thenReturn(companyEntities);
        Page<CompanyEntity> expectedCompanyEntitiesList = companyService.findAll(pageable);

        assertEquals(expectedCompanyEntitiesList.get().count(), 4);
        verify(companyRepository, times(1)).findAll(pageable);
    }

    @Test
    public void findAllIsEmpty() {
        pageable = PageRequest.of(0, 4, Sort.by("name").ascending());
        Page<CompanyEntity> companyEntities = new PageImpl<>(new ArrayList<>());

        when(companyRepository.findAll(pageable)).thenReturn(companyEntities);
        Page<CompanyEntity> expectedCompanyEntitiesList = companyService.findAll(pageable);

        assertThat(expectedCompanyEntitiesList).isEmpty();
        verify(companyRepository, times(1)).findAll(pageable);
    }

    @Test
    public void findAllIsNull() {
        pageable = PageRequest.of(0, 4, Sort.by("name").ascending());

        when(companyRepository.findAll(pageable)).thenReturn(null);
        Page<CompanyEntity> expectedCompanyEntitiesList = companyService.findAll(pageable);

        assertThat(expectedCompanyEntitiesList).isNull();
        verify(companyRepository, times(1)).findAll(pageable);
    }

    @Test
    public void findOneById() {
        Long id = 1L;
        Optional<CompanyEntity> companyEntity = Optional.of(new CompanyEntity(id, "Fake", Rating.ONE_STAR, Set.of()));
        when(companyRepository.findById(id)).thenReturn(companyEntity);
        CompanyDTO actualCompany = companyService.findOne(id);
        assertEquals(id, actualCompany.getId());
    }

    @Test
    public void findOneByIdNotFound() {
        Long id = 2L;
        when(companyRepository.findById(id)).thenReturn(Optional.empty());
        CompanyDTO actualCompany = companyService.findOne(id);
        assertNull(actualCompany);
    }

    @Test
    public void getCompanyIdByName() {
        Long id = 1L;
        CompanyEntity companyEntity = new CompanyEntity(id, "Fake");
        when(companyRepository.getCompanyIdByName(companyEntity.getName())).thenReturn(id);
        Long actualId = companyService.getCompanyIdByName(companyEntity.getName());
        assertEquals(id, actualId);
    }

    @Test
    public void getCompanyIdByNameNotFound() {
        when(companyRepository.getCompanyIdByName("F")).thenReturn(null);
        Long actualId = companyService.getCompanyIdByName("F");
        assertNull(actualId);
    }

}
