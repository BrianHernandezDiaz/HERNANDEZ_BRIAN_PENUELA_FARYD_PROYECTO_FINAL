package com.backend.clinica_odontologica.service.impl;

import com.backend.clinica_odontologica.dto.entrada.DomicilioEntradaDto;
import com.backend.clinica_odontologica.dto.entrada.PacienteEntradaDto;
import com.backend.clinica_odontologica.dto.salida.PacienteSalidaDto;
import com.backend.clinica_odontologica.entity.Paciente;
import com.backend.clinica_odontologica.repository.PacienteRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PacienteServiceTest {

    @Autowired
    private PacienteService pacienteService;

    @MockBean
    private PacienteRepository pacienteRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void registrarPacienteTest() {
        DomicilioEntradaDto domicilioEntradaDto = new DomicilioEntradaDto("Calle 123", 123, "Localidad", "Provincia");
        PacienteEntradaDto entradaDto = new PacienteEntradaDto("Juan", "Pérez", 12345678, LocalDate.now(), domicilioEntradaDto);
        Paciente paciente = modelMapper.map(entradaDto, Paciente.class);
        PacienteSalidaDto salidaDto = modelMapper.map(paciente, PacienteSalidaDto.class);
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(paciente);

        PacienteSalidaDto result = pacienteService.registrarPaciente(entradaDto);

        assertNotNull(result);
        assertEquals(salidaDto.getNombre(), result.getNombre());
        verify(pacienteRepository, times(1)).save(any(Paciente.class));
    }

    @Test
    public void listarPacientesTest() {
        Paciente paciente1 = new Paciente(1L, "Juan", "Pérez", 12345678, LocalDate.now(), null);
        Paciente paciente2 = new Paciente(2L, "Ana", "Martínez", 87654321, LocalDate.now(), null);
        when(pacienteRepository.findAll()).thenReturn(List.of(paciente1, paciente2));

        List<PacienteSalidaDto> result = pacienteService.listarPacientes();

        assertEquals(2, result.size());
        verify(pacienteRepository, times(1)).findAll();
    }

    @Test
    public void buscarPacientePorIdTest() {
        Paciente paciente = new Paciente(1L, "Juan", "Pérez", 12345678, LocalDate.now(), null);
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));

        PacienteSalidaDto result = pacienteService.buscarPacientePorId(1L);

        assertNotNull(result);
        assertEquals("Juan", result.getNombre());
        verify(pacienteRepository, times(1)).findById(1L);
    }

    @Test
    public void eliminarPacienteTest() {
        Paciente paciente = new Paciente(1L, "Juan", "Pérez", 12345678, LocalDate.now(), null);
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));
        doNothing().when(pacienteRepository).deleteById(1L);

        pacienteService.eliminarPaciente(1L);

        verify(pacienteRepository, times(1)).deleteById(1L);
    }

    @Test
    public void actualizarPacienteTest() {
        DomicilioEntradaDto domicilioEntradaDto = new DomicilioEntradaDto("Calle 123", 123, "Localidad", "Provincia");
        PacienteEntradaDto entradaDto = new PacienteEntradaDto("Juan", "Pérez", 12345678, LocalDate.now(), domicilioEntradaDto);
        Paciente paciente = new Paciente(1L, "Juan", "Pérez", 12345678, LocalDate.now(), null);
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(paciente);

        PacienteSalidaDto result = pacienteService.actualizarPaciente(entradaDto, 1L);

        assertNotNull(result);
        assertEquals("Juan", result.getNombre());
        verify(pacienteRepository, times(1)).save(any(Paciente.class));
    }
}
