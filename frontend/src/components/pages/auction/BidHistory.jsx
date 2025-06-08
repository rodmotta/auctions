import { formatCurrencyBR, formatDateTimeBR } from '../../../utils/formatterUtils';
import { Users } from "lucide-react";

function BidHistory({ bids }) {
    return (
        <div className='p-4 border border-stone-300 rounded-xl'>
            <div className='flex items-center gap-2 mb-4'>
                <Users />
                <p className='text-2xl font-semibold'>Últimos Lances</p>
            </div>

            <div className='flex flex-col gap-4'>
                {bids.map(bid => (
                    <div className='flex justify-between items-center px-4 py-2 bg-stone-100 rounded-xl'>
                        <div>
                            <p className='font-semibold'>{bid.bidderName}</p>
                            <p className='text-stone-500'>{formatDateTimeBR(bid.timestamp)}</p>
                        </div>
                        <div>
                            <p className='font-bold text-lg'>R$ {formatCurrencyBR(bid.amount)}</p>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    )
}

export default BidHistory