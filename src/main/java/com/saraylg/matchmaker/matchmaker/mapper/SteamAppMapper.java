package com.saraylg.matchmaker.matchmaker.mapper;

import com.saraylg.matchmaker.matchmaker.dto.input.SteamAppDetailsInputDTO;
import com.saraylg.matchmaker.matchmaker.dto.output.SteamAppOutputDTO;
import com.saraylg.matchmaker.matchmaker.model.SteamAppEntity;
import com.saraylg.matchmaker.matchmaker.model.generic.GenericSteamApp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SteamAppMapper {

    @Mapping(target = "appid", source = "appid")
    @Mapping(target = "name", source = "appDetails.data.name")
    @Mapping(target = "shortDescription", source = "appDetails.data.shortDescription")
    @Mapping(target = "categories", source = "appDetails.data.categories", qualifiedByName = "mapCategories")
    @Mapping(target = "headerImage", source = "appDetails.data.headerImage")
    @Mapping(target = "lastUpdated", expression = "java(java.time.Instant.now())")
    SteamAppEntity toEntity(Long appid, SteamAppDetailsInputDTO.AppDetails appDetails);

    @Named("mapCategories")
    default List<String> mapCategories(List<SteamAppDetailsInputDTO.Category> categories) {
        if (categories == null) return null;
        return categories.stream()
                .map(SteamAppDetailsInputDTO.Category::getDescription)
                .collect(Collectors.toList());
    }

    SteamAppOutputDTO genericToOutput(GenericSteamApp genericSteamApp);
    
    GenericSteamApp entityToGeneric(SteamAppEntity steamAppEntity);

    List<SteamAppOutputDTO> genericListToOutput(List<GenericSteamApp> genericSteamApp);

    default Optional<SteamAppOutputDTO> genericOptionalToOutput(Optional<GenericSteamApp> genericSteamApp) {
        return genericSteamApp.map(this::genericToOutput);
    }
}
