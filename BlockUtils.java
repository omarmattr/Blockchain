import java.util.List;

public class BlockUtils {
    private List<Block> blockChain;
    public List<Block> getBlockChain(){
        return  blockChain;
    }
    public BlockUtils(List<Block> blockChain ){
        this.blockChain =blockChain;
    }
    private void createGenesisBlock(){
        blockChain.add(new Block(0,"0","Hello"));
    }
    public Block getLastBlock(){
        if (blockChain.isEmpty()){
            createGenesisBlock();
        }

        return blockChain.get(blockChain.size()-1);
    }
    public void addBlock(String data){
        Block previousBlock = getLastBlock();
        Block newBlock = new Block(previousBlock.getIndex()+1 ,data, previousBlock.getHash());
        blockChain.add(newBlock);
    }
    public boolean isValid(){
        for (int i = 1; i < blockChain.size(); i++) {
            Block currentBlock =blockChain.get(i);
            Block previousBlock = blockChain.get(i-1);
            if (!currentBlock.getHash().equals(currentBlock.calculateHash())){
                return false;
            }
            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())){
                return false;
            }
        }
            return true;
    }
}