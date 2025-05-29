package com.java.Travel.service.ServiceImpl;

import com.java.Travel.controller.dto.BusDTO;
import com.java.Travel.controller.dto.CompanyDTO;
import com.java.Travel.exception.UserNotFoundException;
import com.java.Travel.model.CompanyEntity;
import com.java.Travel.repository.CompanyEntityRepository;
import com.java.Travel.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyEntityRepository companyRepository;


    @Override
    public Page<CompanyEntity> findAll(Pageable pageable) {
        return companyRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        companyRepository.deleteById(id);
    }

    @Override
    public CompanyDTO findOne(Long id) {
        Optional<CompanyEntity> companyEntity = companyRepository.findById(id);
        if (companyEntity.isPresent()) {
            CompanyDTO companyDTO = new CompanyDTO();
            companyDTO.setId(companyEntity.get().getId());
            companyDTO.setName(companyEntity.get().getName());
            companyDTO.setRating(companyEntity.get().getRating());

            List<BusDTO> busDTOList = companyEntity
                    .get()
                    .getBuses()
                    .stream()
                    .map(p -> new BusDTO(p.getId(), p.getName(), p.getSideNumber(),
                            new CompanyDTO(p.getCompany().getId(), p.getCompany().getName(), p.getCompany().getRating())))
                    .collect(Collectors.toList());

            busDTOList.sort(Comparator.comparing(o -> o.getName()));
            companyDTO.setBusDTOList(busDTOList);
            return companyDTO;
        }
        return null;
    }


    @Override
    public Long getCompanyIdByName(String name) {
        return companyRepository.getCompanyIdByName(name);
    }

    @Transactional
    @Override
    public void save(CompanyDTO companyDTO) {
        CompanyEntity companyEntity = new CompanyEntity(companyDTO.getName(), companyDTO.getRating());
        companyRepository.save(companyEntity);
    }

    @Transactional
    @Override
    public void update(CompanyDTO companyDTO) {
        Optional<CompanyEntity> editCompanyEntity = companyRepository.findById(companyDTO.getId());
        if (editCompanyEntity.isPresent()) {
            editCompanyEntity.get().setName(companyDTO.getName());
            editCompanyEntity.get().setRating(companyDTO.getRating());
            companyRepository.save(editCompanyEntity.get());
        } else {
            throw new UserNotFoundException("Company with id=" + companyDTO.getId() + " not found");
        }
    }

    @Override
    public CompanyDTO findCompanyByName(String company) {
        CompanyEntity companyEntity = companyRepository.findByName(company);
        CompanyDTO companyDTO = new CompanyDTO();
        if (companyEntity != null) {
            companyDTO.setId(companyEntity.getId());
            companyDTO.setName(companyEntity.getName());
        } else {
            throw new EntityNotFoundException("Company with name=" + company + " not found!");
        }
        return companyDTO;
    }

    @Override
    public List<CompanyDTO> findAll() {
        List<CompanyEntity> companyEntities = companyRepository.findAllAndOrderByName();
        return companyEntities.stream().map(a -> new CompanyDTO(a.getId(), a.getName())).collect(Collectors.toList());
    }

    @Override
    public Page<CompanyEntity> findAllByCriteria(PageRequest pageRequest, String name) {
        return companyRepository.findAllByNameIgnoreCase(name, pageRequest);
    }

    public List<BusDTO> checkBusDTOList(List<BusDTO> busDTOList) {
        return busDTOList.size() == 0 || busDTOList == null ? null : busDTOList;
    }
}
