package com.java.Travel.service.ServiceImpl;

import java.util.List;

import com.java.Travel.controller.dto.BusDTO;
import com.java.Travel.controller.dto.CompanyDTO;
import com.java.Travel.exception.EditBusParametersExistException;
import com.java.Travel.model.BusEntity;
import com.java.Travel.repository.BusEntityRepository;
import com.java.Travel.repository.CompanyEntityRepository;
import com.java.Travel.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class BusServiceImpl implements BusService {

    @Autowired
    private BusEntityRepository busRepository;
    @Autowired
    private CompanyEntityRepository companyRepository;


    @Transactional
    @Override
    public BusEntity add(BusEntity bus) {
        BusEntity savedBus = busRepository.save(bus);
        return savedBus;
    }

    @Override
    public List<BusEntity> getAll() {
        return null;
    }


    @Override
    public BusEntity findById() {
        return null;
    }

    @Override
    public BusDTO findOne(Long id) {
        Optional<BusEntity> busEntity = busRepository.findById(id);

        if (busEntity.isPresent()) {
            BusDTO busDTO = new BusDTO();
            busDTO.setId(busEntity.get().getId());
            busDTO.setName(busEntity.get().getName());
            busDTO.setSideNumber(busEntity.get().getSideNumber());

            CompanyDTO companyDTO = new CompanyDTO();
            companyDTO.setName(busEntity.get().getCompany().getName());
            companyDTO.setId(busEntity.get().getCompany().getId());
            busDTO.setCompanyDTO(companyDTO);

            return busDTO;
        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        busRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void saveOrUpdate(BusDTO busDTO, Long companyId, String companyName) {

        Long idExistBus = this.getBusIdBySideNumber(busDTO.getSideNumber());
        String firstLetterСompName = companyName.substring(0, 1);
        String firstLetterSideNumber = busDTO.getSideNumber().substring(0, 1);

        if (!firstLetterСompName.equals(firstLetterSideNumber)) {
            throw new EditBusParametersExistException(
                    "The_first_letter_of_the_tail_number_must_match_the_name_of_the_company",
                    busDTO, companyName, companyId);
        }

        if (busDTO.getId() != null) {

            BusEntity busEntityEdit = busRepository.findById(busDTO.getId()).get();
            if (busEntityEdit != null) {
                if (idExistBus != null && idExistBus != busDTO.getId()) {
                    throw new EditBusParametersExistException(
                            "Bus_with_this_side_number_already_exist", busDTO, companyName, companyId);
                }
                busEntityEdit.setName(busDTO.getName());
                busEntityEdit.setSideNumber(busDTO.getSideNumber());
                busRepository.save(busEntityEdit);
            } else {
                throw new EntityNotFoundException("Bus with id=" + busDTO.getId() + " not found");
            }

        } else {
            if (idExistBus != null && idExistBus != busDTO.getId()) {
                throw new EditBusParametersExistException(
                        "Bus_with_this_side_number_already_exist", busDTO, companyName, companyId);
            }
            BusEntity busEntity = new BusEntity(
                    busDTO.getName(), busDTO.getSideNumber(), companyRepository.findByName(companyName));
            busRepository.save(busEntity);
        }
    }

    @Override
    public Long getCompanyIdByBusId(Long id) {
        return busRepository.getCompanyIdByBusId(id);
    }

    @Override
    public Long getBusIdBySideNumber(String sideNumber) {
        return busRepository.findIdByNumber(sideNumber);
    }
}
