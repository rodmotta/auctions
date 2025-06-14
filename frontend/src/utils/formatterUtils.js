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

export function formatTimeRemaining(deadline) {
  const now = new Date();
  const target = new Date(deadline);
  let diffMs = target - now;

  if (diffMs <= 0) return "Finalizado";

  const minutes = Math.floor(diffMs / (1000 * 60)) % 60;
  const hours = Math.floor(diffMs / (1000 * 60 * 60)) % 24;
  const days = Math.floor(diffMs / (1000 * 60 * 60 * 24));

  if (days >= 1) {
    return `${days}d ${hours}h`;
  }
  return `${hours}h ${minutes}m`;
}
