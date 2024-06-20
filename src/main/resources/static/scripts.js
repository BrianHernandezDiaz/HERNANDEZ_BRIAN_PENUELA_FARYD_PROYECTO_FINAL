document.addEventListener("DOMContentLoaded", function() {
    const pacienteForm = document.getElementById("pacienteForm");
    const odontologoForm = document.getElementById("odontologoForm");
    const turnoForm = document.getElementById("turnoForm");

    const buscarPacienteForm = document.getElementById("buscarPacienteForm");
    const buscarOdontologoForm = document.getElementById("buscarOdontologoForm");
    const buscarTurnoForm = document.getElementById("buscarTurnoForm");

    const listarPacientesBtn = document.getElementById("listarPacientesBtn");
    const listarOdontologosBtn = document.getElementById("listarOdontologosBtn");
    const listarTurnosBtn = document.getElementById("listarTurnosBtn");

    const actualizarPacienteForm = document.getElementById("actualizarPacienteForm");
    const actualizarOdontologoForm = document.getElementById("actualizarOdontologoForm");
    const actualizarTurnoForm = document.getElementById("actualizarTurnoForm");

    const eliminarPacienteForm = document.getElementById("eliminarPacienteForm");
    const eliminarOdontologoForm = document.getElementById("eliminarOdontologoForm");
    const eliminarTurnoForm = document.getElementById("eliminarTurnoForm");

    pacienteForm.addEventListener("submit", function(event) {
        event.preventDefault();
        const pacienteData = {
            nombre: document.getElementById("pacienteNombre").value,
            apellido: document.getElementById("pacienteApellido").value,
            dni: document.getElementById("pacienteDni").value,
            fechaIngreso: document.getElementById("pacienteFechaIngreso").value,
            domicilioEntradaDto: {
                calle: document.getElementById("pacienteCalle").value,
                numero: document.getElementById("pacienteNumero").value,
                localidad: document.getElementById("pacienteLocalidad").value,
                provincia: document.getElementById("pacienteProvincia").value
            }
        };

        fetch("http://localhost:8080/pacientes/registrar", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(pacienteData)
        })
        .then(response => response.json())
        .then(data => {
            alert("Paciente registrado exitosamente");
            pacienteForm.reset();
        })
        .catch(error => {
            console.error("Error:", error);
        });
    });

    odontologoForm.addEventListener("submit", function(event) {
        event.preventDefault();
        const odontologoData = {
            matricula: document.getElementById("odontologoMatricula").value,
            nombre: document.getElementById("odontologoNombre").value,
            apellido: document.getElementById("odontologoApellido").value
        };

        fetch("http://localhost:8080/odontologos/registrar", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(odontologoData)
        })
        .then(response => response.json())
        .then(data => {
            alert("Odontólogo registrado exitosamente");
            odontologoForm.reset();
        })
        .catch(error => {
            console.error("Error:", error);
        });
    });

    turnoForm.addEventListener("submit", function(event) {
        event.preventDefault();
        const turnoData = {
            fechaYHora: document.getElementById("turnoFechaYHora").value,
            odontologoId: document.getElementById("turnoOdontologoId").value,
            pacienteId: document.getElementById("turnoPacienteId").value
        };

        fetch("http://localhost:8080/turnos/registrar", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(turnoData)
        })
        .then(response => response.json())
        .then(data => {
            alert("Turno registrado exitosamente");
            turnoForm.reset();
        })
        .catch(error => {
            console.error("Error:", error);
        });
    });

    buscarPacienteForm.addEventListener("submit", function(event) {
        event.preventDefault();
        const pacienteId = document.getElementById("buscarPacienteId").value;

        fetch(`http://localhost:8080/pacientes/${pacienteId}`)
        .then(response => response.json())
        .then(data => {
            const resultDiv = document.getElementById("buscarPacienteResultado");
            resultDiv.innerHTML = `<pre>${JSON.stringify(data, null, 2)}</pre>`;
        })
        .catch(error => {
            console.error("Error:", error);
        });
    });

    buscarOdontologoForm.addEventListener("submit", function(event) {
        event.preventDefault();
        const odontologoId = document.getElementById("buscarOdontologoId").value;

        fetch(`http://localhost:8080/odontologos/${odontologoId}`)
        .then(response => response.json())
        .then(data => {
            const resultDiv = document.getElementById("buscarOdontologoResultado");
            resultDiv.innerHTML = `<pre>${JSON.stringify(data, null, 2)}</pre>`;
        })
        .catch(error => {
            console.error("Error:", error);
        });
    });

    buscarTurnoForm.addEventListener("submit", function(event) {
        event.preventDefault();
        const turnoId = document.getElementById("buscarTurnoId").value;

        fetch(`http://localhost:8080/turnos/${turnoId}`)
        .then(response => response.json())
        .then(data => {
            const resultDiv = document.getElementById("buscarTurnoResultado");
            resultDiv.innerHTML = `<pre>${JSON.stringify(data, null, 2)}</pre>`;
        })
        .catch(error => {
            console.error("Error:", error);
        });
    });

    listarPacientesBtn.addEventListener("click", function() {
        fetch("http://localhost:8080/pacientes/listar")
        .then(response => response.json())
        .then(data => {
            const resultDiv = document.getElementById("listarPacientesResultado");
            resultDiv.innerHTML = `<pre>${JSON.stringify(data, null, 2)}</pre>`;
        })
        .catch(error => {
            console.error("Error:", error);
        });
    });

    listarOdontologosBtn.addEventListener("click", function() {
        fetch("http://localhost:8080/odontologos/listar")
        .then(response => response.json())
        .then(data => {
            const resultDiv = document.getElementById("listarOdontologosResultado");
            resultDiv.innerHTML = `<pre>${JSON.stringify(data, null, 2)}</pre>`;
        })
        .catch(error => {
            console.error("Error:", error);
        });
    });

    listarTurnosBtn.addEventListener("click", function() {
        fetch("http://localhost:8080/turnos/listar")
        .then(response => response.json())
        .then(data => {
            const resultDiv = document.getElementById("listarTurnosResultado");
            resultDiv.innerHTML = `<pre>${JSON.stringify(data, null, 2)}</pre>`;
        })
        .catch(error => {
            console.error("Error:", error);
        });
    });

    actualizarPacienteForm.addEventListener("submit", function(event) {
        event.preventDefault();
        const pacienteId = document.getElementById("actualizarPacienteId").value;
        const pacienteData = {
            nombre: document.getElementById("actualizarPacienteNombre").value,
            apellido: document.getElementById("actualizarPacienteApellido").value,
            dni: document.getElementById("actualizarPacienteDni").value,
            fechaIngreso: document.getElementById("actualizarPacienteFechaIngreso").value,
            domicilioEntradaDto: {
                calle: document.getElementById("actualizarPacienteCalle").value,
                numero: document.getElementById("actualizarPacienteNumero").value,
                localidad: document.getElementById("actualizarPacienteLocalidad").value,
                provincia: document.getElementById("actualizarPacienteProvincia").value
            }
        };

        fetch(`http://localhost:8080/pacientes/actualizar/${pacienteId}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(pacienteData)
        })
        .then(response => response.json())
        .then(data => {
            alert("Paciente actualizado exitosamente");
            actualizarPacienteForm.reset();
        })
        .catch(error => {
            console.error("Error:", error);
        });
    });

    actualizarOdontologoForm.addEventListener("submit", function(event) {
        event.preventDefault();
        const odontologoId = document.getElementById("actualizarOdontologoId").value;
        const odontologoData = {
            matricula: document.getElementById("actualizarOdontologoMatricula").value,
            nombre: document.getElementById("actualizarOdontologoNombre").value,
            apellido: document.getElementById("actualizarOdontologoApellido").value
        };

        fetch(`http://localhost:8080/odontologos/actualizar/${odontologoId}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(odontologoData)
        })
        .then(response => response.json())
        .then(data => {
            alert("Odontólogo actualizado exitosamente");
            actualizarOdontologoForm.reset();
        })
        .catch(error => {
            console.error("Error:", error);
        });
    });

    actualizarTurnoForm.addEventListener("submit", function(event) {
        event.preventDefault();
        const turnoId = document.getElementById("actualizarTurnoId").value;
        const turnoData = {
            fechaYHora: document.getElementById("actualizarTurnoFechaYHora").value,
            odontologoId: document.getElementById("actualizarTurnoOdontologoId").value,
            pacienteId: document.getElementById("actualizarTurnoPacienteId").value
        };

        fetch(`http://localhost:8080/turnos/actualizar/${turnoId}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(turnoData)
        })
        .then(response => response.json())
        .then(data => {
            alert("Turno actualizado exitosamente");
            actualizarTurnoForm.reset();
        })
        .catch(error => {
            console.error("Error:", error);
        });
    });

    eliminarPacienteForm.addEventListener("submit", function(event) {
        event.preventDefault();
        const pacienteId = document.getElementById("eliminarPacienteId").value;

        fetch(`http://localhost:8080/pacientes/eliminar?id=${pacienteId}`, {
            method: "DELETE"
        })
        .then(() => {
            alert("Paciente eliminado exitosamente");
            eliminarPacienteForm.reset();
        })
        .catch(error => {
            console.error("Error:", error);
        });
    });

    eliminarOdontologoForm.addEventListener("submit", function(event) {
        event.preventDefault();
        const odontologoId = document.getElementById("eliminarOdontologoId").value;

        fetch(`http://localhost:8080/odontologos/eliminar?id=${odontologoId}`, {
            method: "DELETE"
        })
        .then(() => {
            alert("Odontólogo eliminado exitosamente");
            eliminarOdontologoForm.reset();
        })
        .catch(error => {
            console.error("Error:", error);
        });
    });

    eliminarTurnoForm.addEventListener("submit", function(event) {
        event.preventDefault();
        const turnoId = document.getElementById("eliminarTurnoId").value;

        fetch(`http://localhost:8080/turnos/eliminar?id=${turnoId}`, {
            method: "DELETE"
        })
        .then(() => {
            alert("Turno eliminado exitosamente");
            eliminarTurnoForm.reset();
        })
        .catch(error => {
            console.error("Error:", error);
        });
    });
});
