import { formatCurrencyBR, formatDateTimeBR } from '../../../utils/formatterUtils';
import { User } from "lucide-react";
import BorderBox from '../../shared/BorderBox';

function BidsHistoryCard({ bids }) {
    return (
        <BorderBox className="mt-4">
            <h3 className="mb-4 font-semibold text-lg">Maiores lances</h3>
            <ul className="flex flex-col gap-4">
                {bids.map(bid => (
                    <li key={bid.id}>
                        <BorderBox className="flex justify-between items-center px-4 py-2">
                            <div className="flex items-center">
                                <User className="mr-2 h-4 w-4" />
                                <span>{bid.bidderName}</span>
                            </div>
                            <span>{formatDateTimeBR(bid.timestamp)}</span>
                            <span className="text-xl font-bold">R$ {formatCurrencyBR(bid.amount)}</span>
                        </BorderBox>
                    </li>
                )) ?? <p>Nenhum lance encontrado</p>}
            </ul>
        </BorderBox>
    )
}

export default BidsHistoryCard