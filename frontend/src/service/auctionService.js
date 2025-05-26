import axios from "axios";

async function getAuctions() {
    const response = await axios.get("http://localhost:8080/auctions");
    //todo - validate error
    return response.data;
}

async function getAuctionsById(auctionId) {
    const response = await axios.get(`http://localhost:8080/auctions/${auctionId}`);
    //todo - validate error
    return response.data;
}

export { getAuctions, getAuctionsById }