package com.hotelbooking.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.hotelbooking.models.Hotel;
import com.hotelbooking.models.Owner;
import com.hotelbooking.repositories.HotelRepository;
import com.hotelbooking.repositories.OwnerRepository;
import com.hotelbooking.services.HotelService;


class GetHotelByOwnerTest {

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private HotelService hotelService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetHotelsByOwner() {
        String ownerId = "owner123";
        Owner owner = new Owner();
        owner.setOwnerId(ownerId);

        Hotel hotel1 = new Hotel();
        hotel1.setHotelId("hotel1");
        hotel1.setHotelName("Hotel1");
        hotel1.setOwner(owner);

        Hotel hotel2 = new Hotel();
        hotel2.setHotelId("hotel2");
        hotel2.setHotelName("Hotel2");
        hotel2.setOwner(owner);

        List<Hotel> hotels = Arrays.asList(hotel1, hotel2);

        when(ownerRepository.findById(ownerId)).thenReturn(Optional.of(owner));
        when(hotelRepository.findByOwner(owner)).thenReturn(hotels);

        List<Hotel> result = hotelService.getHotelsByOwner(ownerId);

        assertEquals(2, result.size());
        assertEquals("Hotel1", result.get(0).getHotelName());
        assertEquals("Hotel2", result.get(1).getHotelName());
    }

    @Test
    public void testGetHotelsByOwnerOwnerNotFound() {
        String ownerId = "owner123";

        when(ownerRepository.findById(ownerId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            hotelService.getHotelsByOwner(ownerId);
        });

        assertEquals("Owner not found", exception.getMessage());
    }
}
