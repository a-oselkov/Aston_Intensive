package org.sasha.service.impl;

import jakarta.transaction.Transactional;
import org.sasha.model.Location;
import org.sasha.dao.LocationDao;
import org.sasha.dao.WeatherDao;
import org.sasha.dto.WeatherDto.LocationDto;
import org.sasha.dto.WeatherDto.WeatherDto;
import org.sasha.service.LocationService;

import java.util.List;
import java.util.Optional;

@Transactional
public class LocationServiceImpl implements LocationService {
    private final LocationDao locationDao;

    public LocationServiceImpl() {
        this.locationDao = new LocationDao();
    }

    @Override
    public long save(LocationDto location) {
        return locationDao.save(location);
    }

    @Override
    public Optional<Location> findById(Long id) {
        return locationDao.findById(id);
    }

    @Override
    public List<Location> findAll() {
        return locationDao.findAll();
    }

    @Override
    public void deleteById(Long id) {
        locationDao.deleteById(id);
    }

    @Override
    public void deleteAll() {
        locationDao.deleteAll();
    }

    @Override
    public Optional<Location> findByRegion(String region) {
        return locationDao.findByRegion(region);
    }

    @Override
    public LocationDto getLocationData(String weatherApiUrl) {
        WeatherDao weatherDao = new WeatherDao();
        WeatherDto weather = weatherDao.getWeatherData(weatherApiUrl);
        return weather.getLocation();
    }

}
