export function formatTimeRemaining(dateTime) {
  const now = new Date();
  const target = new Date(dateTime);
  const diffMs = target - now; // Diferença em milissegundos

  if (diffMs <= 0) return "Encerrado";

  const diffSec = Math.floor(diffMs / 1000);
  const diffMin = Math.floor(diffSec / 60);
  const diffHours = Math.floor(diffMin / 60);
  const diffDays = Math.floor(diffHours / 24);

  const hours = diffHours % 24;
  const minutes = diffMin % 60;

  const parts = [];
  if (diffDays > 0) parts.push(`${diffDays}d`);
  if (hours > 0) parts.push(`${hours}h`);
  if (minutes > 0 && diffDays === 0) parts.push(`${minutes}min`); // Só mostra minutos se não tiver dias

  return parts.join(" ") || "Menos de 1min";
}

export function formatCurrencyBR(value) {
  const number = typeof value === 'string' ? parseFloat(value) : value;
  return number.toLocaleString('pt-BR', {
    minimumFractionDigits: 2,
    maximumFractionDigits: 2
  });
}

export function formatDateTimeBR(dateTime) {
  const date = new Date(dateTime);

  const options = {
    day: '2-digit',
    month: '2-digit',
    year: 'numeric',
    hour: '2-digit',
    minute: '2-digit',
    hour12: false
  };

  return date.toLocaleDateString('pt-BR', options);
}
