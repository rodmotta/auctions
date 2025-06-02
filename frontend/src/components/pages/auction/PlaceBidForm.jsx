import { useForm } from "react-hook-form";
import Button from "../../shared/Button";
import { placeBid } from "../../../service/bidService";

function PlaceBidForm({auction}) {

    const { register, handleSubmit } = useForm();

    const submitPlaceBid = async (data) => {
        const bidData = { ...data, auctionId: Number(auction.id) };
        await placeBid(bidData);
    }

    const isAuctionFinished = new Date() > new Date(auction.endTime);

    return (
        <form onSubmit={handleSubmit(submitPlaceBid)} className="flex mt-4">
            <input
                className="border border-stone-300 rounded-l-lg flex-1 h-[40px] px-4 py-2"
                type="number"
                required
                {...register("amount")}
            />

            <Button
                variant='filled'
                className='px-4 py-2 rounded-l-none disabled:bg-neutral-500 disabled:cursor-not-allowed'
                type="submit"
                disabled={isAuctionFinished}
                text='Fazer lance'
            />
        </form>
    )
}

export default PlaceBidForm