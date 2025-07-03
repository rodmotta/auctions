import dayjs from "dayjs"
import duration from 'dayjs/plugin/duration'

export function formatCurrencyBR(value) {
  const number = typeof value === 'string' ? parseFloat(value) : value;
  return number.toLocaleString('pt-BR', {
    minimumFractionDigits: 2,
    maximumFractionDigits: 2
  });
}

export function formatTimeRemaining(deadline) {

  dayjs.extend(duration);

  const diff = dayjs(deadline).diff(dayjs());

  const dur = dayjs.duration(diff);

  const days = dur.days();
  const hours = dur.hours();
  const minutes = dur.minutes();

  if (days >= 1) {
    return `${days}d ${hours}h`;
  }
  return `${hours}h ${minutes}m`;
}
