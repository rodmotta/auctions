import { Clock } from 'lucide-react';
import { useNavigate } from 'react-router';
import { formatCurrencyBR, formatTimeRemaining } from '../../../utils/formatterUtils';

function AuctionCard({ auction }) {
    const navigate = useNavigate();

    return (
        <div className="border border-stone-300 hover:border-stone-600 rounded-lg cursor-pointer"
            onClick={() => navigate(`/auction/${auction.id}`)}>
            <figure>
                <img className='rounded-t-lg' src="https://images.unsplash.com/photo-1506610654-064fbba4780c?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D" alt="" />
            </figure>
            <div className="flex items-center bg-stone-600 text-white px-2 py-1">
                <Clock className='mr-2 h-4 w-4' />
                {formatTimeRemaining(auction.endTime)}
            </div>
            <div className="p-4">
                <h2 className="font-semibold text-lg">{auction.title}</h2>
                <div className="leading-tight">
                    <p className="text-stone-500 text-sm">Lance atual</p>
                    <p className="font-bold">R$: {auction.currentBid ? formatCurrencyBR(auction.currentBid) : formatCurrencyBR(auction.startingBid)}</p>
                </div>
            </div>
        </div>
    )
}

export default AuctionCard