document.addEventListener('DOMContentLoaded', function () {
    const checkboxes = document.querySelectorAll('.check-box');

    checkboxes.forEach(checkbox => {
        const taskId = checkbox.getAttribute('data-id');
        const statusLabel = document.querySelector(`label[for="status-${taskId}"] span`);

        // Verifica o texto do status e marca o checkbox se for 'DONE'
        if (statusLabel.textContent === 'DONE') {
            checkbox.checked = true;
        }

        checkbox.addEventListener('change', function (e) {
            const isChecked = e.target.checked;

            // Atualiza o status na interface imediatamente
            statusLabel.textContent = isChecked ? 'DONE' : 'TODO';

            // Faz a requisição para o backend
            fetch('/update-status', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    id: taskId,
                    status: isChecked ? 'DONE' : 'TODO'
                })
            })
            .catch(error => {
                console.error('Error:', error);
                // Reverte a mudança na interface em caso de erro
                checkbox.checked = !isChecked;
                statusLabel.textContent = checkbox.checked ? 'DONE' : 'TODO';
            });
        });
    });
});
