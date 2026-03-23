package com.qubid.backend.services.Impl;

import com.qubid.backend.dtos.Request.FranchiseRequestDto;
import com.qubid.backend.dtos.Response.FranchiseDto;
import com.qubid.backend.dtos.Response.FranchiseResponseDto;
import com.qubid.backend.entities.Franchise;
import com.qubid.backend.repository.FranchiseRepository;
import com.qubid.backend.services.FranchiseService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FranchiseServiceImpl implements FranchiseService {

    private static final String FRANCHISE_NOT_FOUND = "Franchise not found";

    private final FranchiseRepository franchiseRepository;
    private final ModelMapper modelMapper;

    @Override
    public FranchiseResponseDto createFranchise(FranchiseRequestDto dto) {

        if (franchiseRepository.existsFranchiseByName(dto.getName())) {
            throw new RuntimeException("Franchise already exists");
        }

        Franchise franchise = modelMapper.map(dto, Franchise.class);

        Franchise saved = franchiseRepository.save(franchise);

        return modelMapper.map(saved, FranchiseResponseDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FranchiseDto> getAllFranchises() {
        return franchiseRepository.findAll()
                .stream()
                .map(f -> modelMapper.map(f, FranchiseDto.class))
                .toList();
    }

    @Override
    public FranchiseDto getFranchiseById(Long id) {
        return modelMapper.map(getFranchiseOrThrow(id), FranchiseDto.class);
    }

    @Override
    public FranchiseResponseDto updateFranchise(Long id, FranchiseRequestDto dto) {

        Franchise existing = getFranchiseOrThrow(id);

        modelMapper.map(dto, existing); // auto update fields

        Franchise updated = franchiseRepository.save(existing);

        return modelMapper.map(updated, FranchiseResponseDto.class);
    }

    @Override
    public void deleteFranchise(Long id) {
        getFranchiseOrThrow(id);
        franchiseRepository.deleteById(id);
    }

    @Override
    public Optional<FranchiseDto> getFranchiseByName(String name) {
        return franchiseRepository.findByNameIgnoreCase(name)
                .map(f -> modelMapper.map(f, FranchiseDto.class));
    }

    @Override
    public List<FranchiseDto> getFranchisesByCountry(String country) {
        return franchiseRepository.findAllByCountryIgnoreCase(country)
                .stream()
                .map(f -> modelMapper.map(f, FranchiseDto.class))
                .toList();
    }

    @Override
    public List<FranchiseDto> searchFranchisesByName(String namePart) {
        return franchiseRepository.searchByName(namePart)
                .stream()
                .map(f -> modelMapper.map(f, FranchiseDto.class))
                .toList();
    }

    @Override
    public FranchiseResponseDto getFranchiseByIdWithDetails(Long id) {
        Franchise franchise = franchiseRepository.findDetailedById(id)
                .orElseThrow(() -> new RuntimeException(FRANCHISE_NOT_FOUND));

        return modelMapper.map(franchise, FranchiseResponseDto.class);
    }

    @Override
    public List<FranchiseResponseDto> getAllFranchisesWithDetails() {
        return franchiseRepository.findAllDetailed()
                .stream()
                .map(f -> modelMapper.map(f, FranchiseResponseDto.class))
                .toList();
    }

    private Franchise getFranchiseOrThrow(Long id) {
        return franchiseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(FRANCHISE_NOT_FOUND));
    }
}
