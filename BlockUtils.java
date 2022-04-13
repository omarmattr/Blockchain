import java.util.ArrayList;
import java.util.List;

public class BlockUtils {
    private List<Block> blockChain;
    private List<String> data = new ArrayList<>() ;
    public List<Block> getBlockChain(){
        return  blockChain;
    }
    public BlockUtils(List<Block> blockChain ){
        this.blockChain =blockChain;
    }
    private void createGenesisBlock(){
        Header header = new Header("0","0",2);
        data.add("Hello");
        blockChain.add(new Block(0,header,data));
    }
    public Block getLastBlock(){
        if (blockChain.isEmpty()){
            createGenesisBlock();
        }

        return blockChain.get(blockChain.size()-1);
    }
    public void addNewTransaction(String transaction){
        data.add(transaction);
    }
    private void addBlock(){
        Block previousBlock = getLastBlock();
        Header header = new Header("0",previousBlock.getHash(),2);
        Block newBlock = new Block(previousBlock.getIndex()+1 ,header,data);
        blockChain.add(newBlock);
    }
    public boolean isValid(){
        for (int i = 1; i < blockChain.size(); i++) {
            Block currentBlock =blockChain.get(i);
            Block previousBlock = blockChain.get(i-1);
            if (!currentBlock.getHash().equals(currentBlock.calculateHash())){
                return false;
            }
            if (!currentBlock.getHeader().getPreviousHash().equals(previousBlock.getHash())){
                return false;
            }
        }
            return true;
    }
    public void mineBlock() {
      addBlock();
        data = new ArrayList<>();
    }
}
