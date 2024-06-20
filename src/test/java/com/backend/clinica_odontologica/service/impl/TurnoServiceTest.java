package com.backend.clinica_odontologica.service.impl;

import com.backend.clinica_odontologica.dto.entrada.TurnoEntradaDto;
import com.backend.clinica_odontologica.dto.salida.TurnoSalidaDto;
import com.backend.clinica_odontologica.entity.Odontologo;
import com.backend.clinica_odontologica.entity.Paciente;
import com.backend.clinica_odontologica.entity.Turno;
import com.backend.clinica_odontologica.repository.OdontologoRepository;
import com.backend.clinica_odontologica.repository.PacienteRepository;
import com.backend.clinica_odontologica.repository.TurnoRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TurnoServiceTest {

    @Autowired
    private TurnoService turnoService;

    @MockBean
    private TurnoRepository turnoRepository;

    @MockBean
    private OdontologoRepository odontologoRepository;

    @MockBean
    private PacienteRepository pacienteRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void registrarTurnoTest() {
        TurnoEntradaDto entradaDto = new TurnoEntradaDto(LocalDateTime.now(), 1L, 1L);
        Turno turno = new Turno(1L, entradaDto.getFechaYHora(), new Odontologo(), new Paciente());
        TurnoSalidaDto salidaDto = modelMapper.map(turno, TurnoSalidaDto.class);

        when(odontologoRepository.findById(1L)).thenReturn(Optional.of(new Odontologo()));
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(new Paciente()));
        when(turnoRepository.save(any(Turno.class))).thenReturn(turno);

        TurnoSalidaDto result = turnoService.registrarTurno(entradaDto);

        assertNotNull(result);
        assertEquals(salidaDto.getFechaYHora(), result.getFechaYHora());
        verify(turnoRepository, times(1)).save(any(Turno.class));
    }

    @Test
    public void listarTurnosTest() {
        Turno turno1 = new Turno(1L, LocalDateTime.now(), new Odontologo(), new Paciente());
        Turno turno2 = new Turno(2L, LocalDateTime.now(), new Odontologo(), new Paciente());
        when(turnoRepository.findAll()).thenReturn(List.of(turno1, turno2));

        List<TurnoSalidaDto> result = turnoService.listarTurnos();

        assertEquals(2, result.size());
        verify(turnoRepository, times(1)).findAll();
    }

    @Test
    public void buscarTurnoPorIdTest() {
        Turno turno = new Turno(1L, LocalDateTime.now(), new Odontologo(), new Paciente());
        when(turnoRepository.findById(1L)).thenReturn(Optional.of(turno));

        TurnoSalidaDto result = turnoService.buscarTurnoPorId(1L);

        assertNotNull(result);
        assertEquals(turno.getId(), result.getId());
        verify(turnoRepository, times(1)).findById(1L);
    }

    @Test
    public void eliminarTurnoTest() {
        Turno turno = new Turno(1L, LocalDateTime.now(), new Odontologo(), new Paciente());
        when(turnoRepository.findById(1L)).thenReturn(Optional.of(turno));
        doNothing().when(turnoRepository).deleteById(1L);

        turnoService.eliminarTurno(1L);

        verify(turnoRepository, times(1)).deleteById(1L);
    }

    @Test
    public void actualizarTurnoTest() {
        TurnoEntradaDto entradaDto = new TurnoEntradaDto(LocalDateTime.now(), 1L, 1L);
        Turno turno = new Turno(1L, entradaDto.getFechaYHora(), new Odontologo(), new Paciente());
        when(turnoRepository.findById(1L)).thenReturn(Optional.of(turno));
        when(odontologoRepository.findById(1L)).thenReturn(Optional.of(new Odontologo()));
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(new Paciente()));
        when(turnoRepository.save(any(Turno.class))).thenReturn(turno);

        TurnoSalidaDto result = turnoService.actualizarTurno(entradaDto, 1L);

        assertNotNull(result);
        assertEquals(turno.getFechaYHora(), result.getFechaYHora());
        verify(turnoRepository, times(1)).save(any(Turno.class));
    }
}
