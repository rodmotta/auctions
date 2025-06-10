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
