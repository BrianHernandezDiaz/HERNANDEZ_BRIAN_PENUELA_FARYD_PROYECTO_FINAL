package com.backend.clinica_odontologica.service.impl;

import com.backend.clinica_odontologica.dto.entrada.TurnoEntradaDto;
import com.backend.clinica_odontologica.dto.salida.TurnoSalidaDto;
import com.backend.clinica_odontologica.entity.Odontologo;
import com.backend.clinica_odontologica.entity.Paciente;
import com.backend.clinica_odontologica.entity.Turno;
import com.backend.clinica_odontologica.exceptions.ResourceNotFoundException;
import com.backend.clinica_odontologica.repository.OdontologoRepository;
import com.backend.clinica_odontologica.repository.PacienteRepository;
import com.backend.clinica_odontologica.repository.TurnoRepository;
import com.backend.clinica_odontologica.service.ITurnoService;
import com.backend.clinica_odontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurnoService implements ITurnoService {

    private final Logger LOGGER = LoggerFactory.getLogger(TurnoService.class);
    private final TurnoRepository turnoRepository;
    private final OdontologoRepository odontologoRepository;
    private final PacienteRepository pacienteRepository;
    private final ModelMapper modelMapper;

    public TurnoService(TurnoRepository turnoRepository, OdontologoRepository odontologoRepository, PacienteRepository pacienteRepository, ModelMapper modelMapper) {
        this.turnoRepository = turnoRepository;
        this.odontologoRepository = odontologoRepository;
        this.pacienteRepository = pacienteRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TurnoSalidaDto registrarTurno(TurnoEntradaDto turnoEntradaDto) {
        LOGGER.info("TurnoEntradaDto: " + JsonPrinter.toString(turnoEntradaDto));
        Turno turno = new Turno();
        turno.setFechaYHora(turnoEntradaDto.getFechaYHora());
        Odontologo odontologo = odontologoRepository.findById(turnoEntradaDto.getOdontologoId()).orElse(null);
        Paciente paciente = pacienteRepository.findById(turnoEntradaDto.getPacienteId()).orElse(null);
        if (odontologo != null && paciente != null) {
            turno.setOdontologo(odontologo);
            turno.setPaciente(paciente);
            TurnoSalidaDto turnoSalidaDto = modelMapper.map(turnoRepository.save(turno), TurnoSalidaDto.class);
            LOGGER.info("TurnoSalidaDto: " + JsonPrinter.toString(turnoSalidaDto));
            return turnoSalidaDto;
        } else {
            LOGGER.error("Odontologo o Paciente no encontrado");
            return null;
        }
    }

    @Override
    public List<TurnoSalidaDto> listarTurnos() {
        List<TurnoSalidaDto> turnos = turnoRepository.findAll()
                .stream()
                .map(turno -> modelMapper.map(turno, TurnoSalidaDto.class))
                .toList();
        LOGGER.info("Listado de todos los turnos: {}", JsonPrinter.toString(turnos));
        return turnos;
    }

    @Override
    public TurnoSalidaDto buscarTurnoPorId(Long id) {
        Turno turnoBuscado = turnoRepository.findById(id).orElse(null);
        TurnoSalidaDto turnoEncontrado = null;

        if (turnoBuscado != null) {
            turnoEncontrado = modelMapper.map(turnoBuscado, TurnoSalidaDto.class);
            LOGGER.info("Turno encontrado: {}", JsonPrinter.toString(turnoEncontrado));
        } else {
            LOGGER.error("No se ha encontrado el turno con id {}", id);
        }

        return turnoEncontrado;
    }

    @Override
    public void eliminarTurno(Long id) throws ResourceNotFoundException {
        if (buscarTurnoPorId(id) != null) {
            turnoRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el turno con id {}", id);
        } else {
            throw new ResourceNotFoundException("No existe registro de turno con id " + id);
        }
    }

    @Override
    public TurnoSalidaDto actualizarTurno(TurnoEntradaDto turnoEntradaDto, Long id) {
        Turno turnoRecibido = new Turno();
        turnoRecibido.setFechaYHora(turnoEntradaDto.getFechaYHora());
        Odontologo odontologo = odontologoRepository.findById(turnoEntradaDto.getOdontologoId()).orElse(null);
        Paciente paciente = pacienteRepository.findById(turnoEntradaDto.getPacienteId()).orElse(null);
        Turno turnoAActualizar = turnoRepository.findById(id).orElse(null);
        TurnoSalidaDto turnoSalidaDto = null;

        if (turnoAActualizar != null && odontologo != null && paciente != null) {
            turnoRecibido.setId(turnoAActualizar.getId());
            turnoRecibido.setOdontologo(odontologo);
            turnoRecibido.setPaciente(paciente);
            turnoRepository.save(turnoRecibido);
            turnoSalidaDto = modelMapper.map(turnoRecibido, TurnoSalidaDto.class);
            LOGGER.warn("Turno actualizado: {}", JsonPrinter.toString(turnoSalidaDto));
        } else {
            LOGGER.error("No fue posible actualizar el turno porque no se encuentra en nuestra base de datos");
        }

        return turnoSalidaDto;
    }
}
