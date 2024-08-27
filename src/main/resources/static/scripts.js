const checkboxes = document.querySelectorAll('.check-box');

checkboxes.forEach(checkbox => {
    checkbox.addEventListener('change', function(e) {
        const taskId = e.target.getAttribute('data-id'); // Obtém o ID da tarefa
        const isChecked = e.target.checked; // Obtém o novo status
        fetch('/update-status', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: taskId,  // Envia o ID da tarefa
                status: isChecked ? 'DONE' : 'TODO' // Assume que 'TODO' é marcado e 'DONE' é desmarcado
            })
        })
        .catch(error => {
            console.error('Error:', error);
        });
    });
});
