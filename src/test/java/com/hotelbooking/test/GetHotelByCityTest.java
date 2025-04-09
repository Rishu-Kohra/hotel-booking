package com.hotelbooking.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hotelbooking.models.Hotel;
import com.hotelbooking.repositories.HotelRepository;
import com.hotelbooking.services.HotelService;


public class GetHotelByCityTest {

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private HotelService hotelService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetHotelsByCity() {
        String city = "Amritsar";

        Hotel hotel1 = new Hotel();
        hotel1.setHotelId("hotel1");
        hotel1.setHotelName("Hotel1");
        hotel1.setCity(city);

        Hotel hotel2 = new Hotel();
        hotel2.setHotelId("hotel2");
        hotel2.setHotelName("Hotel2");
        hotel2.setCity(city);

        List<Hotel> hotels = Arrays.asList(hotel1, hotel2);

        when(hotelRepository.findByCity(city)).thenReturn(hotels);

        List<Hotel> result = hotelService.getHotelsByCity(city);

        assertEquals(2, result.size());
        assertEquals("Hotel1", result.get(0).getHotelName());
        assertEquals("Hotel2", result.get(1).getHotelName());
    }
}
