package com.saraylg.matchmaker.matchmaker.mapper;

import com.saraylg.matchmaker.matchmaker.dto.SteamAppDetailsResponse;
import com.saraylg.matchmaker.matchmaker.dto.SteamAppOutputDto;
import com.saraylg.matchmaker.matchmaker.model.SteamAppEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SteamAppMapper {

    @Mapping(target = "appid", source = "appid")
    @Mapping(target = "name", source = "appDetails.data.name")
    @Mapping(target = "shortDescription", source = "appDetails.data.shortDescription")
    @Mapping(target = "categories", source = "appDetails.data.categories", qualifiedByName = "mapCategories")
    @Mapping(target = "headerImage", source = "appDetails.data.headerImage")
    @Mapping(target = "lastUpdated", expression = "java(java.time.Instant.now())")
    SteamAppEntity toEntity(Long appid, SteamAppDetailsResponse.AppDetails appDetails);

    @Named("mapCategories")
    default List<String> mapCategories(List<SteamAppDetailsResponse.Category> categories) {
        if (categories == null) return null;
        return categories.stream()
                .map(SteamAppDetailsResponse.Category::getDescription)
                .collect(Collectors.toList());
    }

    SteamAppOutputDto toOutput(SteamAppEntity steamAppEntity);
}
