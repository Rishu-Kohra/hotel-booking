package com.hotelbooking.test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hotelbooking.models.Hotel;
import com.hotelbooking.models.Inventory;
import com.hotelbooking.models.Owner;
import com.hotelbooking.repositories.HotelRepository;
import com.hotelbooking.repositories.InventoryRepository;
import com.hotelbooking.repositories.OwnerRepository;
import com.hotelbooking.services.InventoryService;

import java.util.*;

public class GetHotelInventoryTest {

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryService inventoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetInventoryByOwner() {
        String ownerId = "owner123";
        Owner owner = new Owner();
        owner.setOwnerId(ownerId);

        Hotel hotel1 = new Hotel();
        hotel1.setHotelId("1");
        Hotel hotel2 = new Hotel();
        hotel2.setHotelId("2");

        Inventory inventory1 = new Inventory();
        inventory1.setInventoryId("1");
        Inventory inventory2 = new Inventory();
        inventory2.setInventoryId("2");

        when(ownerRepository.findById(ownerId)).thenReturn(Optional.of(owner));
        when(hotelRepository.findByOwner(owner)).thenReturn(Arrays.asList(hotel1, hotel2));
        when(inventoryRepository.findHotelInventory(hotel1)).thenReturn(Arrays.asList(inventory1));
        when(inventoryRepository.findHotelInventory(hotel2)).thenReturn(Arrays.asList(inventory2));

        List<List<Inventory>> result = inventoryService.getInventoryByOwner(ownerId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(inventory1, result.get(0).get(0));
        assertEquals(inventory2, result.get(1).get(0));
    }

    @Test
    public void testGetInventoryByOwnerOwnerNotFound() {
        String ownerId = "owner123";

        when(ownerRepository.findById(ownerId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            inventoryService.getInventoryByOwner(ownerId);
        });

        assertEquals("Owner not found", exception.getMessage());
    }
}
