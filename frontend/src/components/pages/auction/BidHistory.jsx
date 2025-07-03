import dayjs from 'dayjs';
import { formatCurrencyBR } from '../../../utils/formatterUtils';
import { Crown, Users } from "lucide-react";

function BidHistory({ bids }) {
    return (
        <div className='p-4 border border-base-200 shadow-md rounded-lg'>
            <div className='flex items-center gap-2 mb-4'>
                <Users />
                <p className='text-2xl font-semibold'>Últimos Lances</p>
            </div>

            {bids.length === 0
                ? (
                    <div className="flex justify-center text-gray-500">
                        <span>Nenhum lance ainda. Seja o primeiro!</span>
                    </div>
                ) : (
                    <div className='flex flex-col gap-4'>
                        {bids.map((bid, idx) => (
                            <div
                                key={idx}
                                className='flex justify-between items-center px-4 py-2 bg-base-200 rounded-lg'>
                                <div>
                                    <p className='text-sm font-semibold'>{bid.bidderName}</p>
                                    <p className='text-sm text-stone-500'>{dayjs(bid.timestamp).format('DD/MM/YYYY, HH:mm')}</p>
                                </div>
                                <div className='flex items-center gap-2'>
                                    {idx === 0 && <Crown className='text-yellow-500 w-5 h-5' />}
                                    <p className='font-bold text-md'>R$ {formatCurrencyBR(bid.amount)}</p>
                                </div>
                            </div>
                        ))}
                    </div>
                )}


        </div>
    )
}

export default BidHistory