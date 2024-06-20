package com.backend.clinica_odontologica.service.impl;

import com.backend.clinica_odontologica.dto.entrada.OdontologoEntradaDto;
import com.backend.clinica_odontologica.dto.salida.OdontologoSalidaDto;
import com.backend.clinica_odontologica.entity.Odontologo;
import com.backend.clinica_odontologica.exceptions.NotFoundException;
import com.backend.clinica_odontologica.repository.OdontologoRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OdontologoServiceTest {

    @Autowired
    private OdontologoService odontologoService;

    @MockBean
    private OdontologoRepository odontologoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void registrarOdontologoTest() {
        OdontologoEntradaDto entradaDto = new OdontologoEntradaDto("12345", "Juan", "Pérez");
        Odontologo odontologo = modelMapper.map(entradaDto, Odontologo.class);
        OdontologoSalidaDto salidaDto = modelMapper.map(odontologo, OdontologoSalidaDto.class);
        when(odontologoRepository.save(any(Odontologo.class))).thenReturn(odontologo);

        OdontologoSalidaDto result = odontologoService.registrarOdontologo(entradaDto);

        assertNotNull(result);
        assertEquals(salidaDto.getNombre(), result.getNombre());
        verify(odontologoRepository, times(1)).save(any(Odontologo.class));
    }

    @Test
    public void listarOdontologosTest() {
        Odontologo odontologo1 = new Odontologo(1L, "12345", "Juan", "Pérez");
        Odontologo odontologo2 = new Odontologo(2L, "67890", "Ana", "Martínez");
        when(odontologoRepository.findAll()).thenReturn(List.of(odontologo1, odontologo2));

        List<OdontologoSalidaDto> result = odontologoService.listarOdontologos();

        assertEquals(2, result.size());
        verify(odontologoRepository, times(1)).findAll();
    }

    @Test
    public void buscarOdontologoPorIdTest() {
        Odontologo odontologo = new Odontologo(1L, "12345", "Juan", "Pérez");
        when(odontologoRepository.findById(1L)).thenReturn(Optional.of(odontologo));

        OdontologoSalidaDto result = odontologoService.buscarOdontologoPorId(1L);

        assertNotNull(result);
        assertEquals("Juan", result.getNombre());
        verify(odontologoRepository, times(1)).findById(1L);
    }

    @Test
    public void eliminarOdontologoTest() {
        Odontologo odontologo = new Odontologo(1L, "12345", "Juan", "Pérez");
        when(odontologoRepository.findById(1L)).thenReturn(Optional.of(odontologo));
        doNothing().when(odontologoRepository).deleteById(1L);

        odontologoService.eliminarOdontologo(1L);

        verify(odontologoRepository, times(1)).deleteById(1L);
    }

    @Test
    public void actualizarOdontologoTest() {
        OdontologoEntradaDto entradaDto = new OdontologoEntradaDto("12345", "Juan", "Pérez");
        Odontologo odontologo = new Odontologo(1L, "12345", "Juan", "Pérez");
        when(odontologoRepository.findById(1L)).thenReturn(Optional.of(odontologo));
        when(odontologoRepository.save(any(Odontologo.class))).thenReturn(odontologo);

        OdontologoSalidaDto result = odontologoService.actualizarOdontologo(entradaDto, 1L);

        assertNotNull(result);
        assertEquals("Juan", result.getNombre());
        verify(odontologoRepository, times(1)).save(any(Odontologo.class));
    }
}
