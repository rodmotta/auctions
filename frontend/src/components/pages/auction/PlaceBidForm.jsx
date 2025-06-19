import { useForm } from "react-hook-form";
import { placeBid } from "../../../service/bidService";
import { formatCurrencyBR } from "../../../utils/formatterUtils";

function PlaceBidForm({ auction }) {

    const { register, handleSubmit } = useForm();

    const submitPlaceBid = async (data) => {
        const bidData = { ...data, auctionId: auction.id };
        await placeBid(bidData);
    }

    return (
        <form onSubmit={handleSubmit(submitPlaceBid)} className='flex flex-col gap-2'>
            <input
                type="number"
                className="border border-stone-300 rounded-lg h-[40px] px-4 py-2"
                placeholder={`Lance mínimo: R$ ${formatCurrencyBR(auction.currentPrice + auction.minimumIncrement)}`}
                min={auction.currentPrice + auction.minimumIncrement}
                required
                {...register("amount")}
            />
            <button className='btn btn-neutral rounded-lg'>Fazer lance</button>
        </form>
    )
}

export default PlaceBidForm