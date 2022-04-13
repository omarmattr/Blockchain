import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

public class BlockChain {
    public static void main(String[] args) {
        //read in file before end system
        BlockUtils blockUtils = new BlockUtils(read());
        //add new Transaction to blockchain
        blockUtils.addNewTransaction("100");
        blockUtils.addNewTransaction("100");
        blockUtils.addNewTransaction("100");
        //mineBlock to blockchain
        blockUtils.mineBlock();
        blockUtils.addNewTransaction("200");
        blockUtils.addNewTransaction("200");
        blockUtils.mineBlock();

        // this line thin return in valid blockchain when editing
        //  blockUtils.getBlockChain().get(1).getData().set(1,"200");

        //print if blockchain is valid
        System.out.println(blockUtils.isValid());

        //write in file before end system
        writer(blockUtils.getBlockChain());
    }

    public static String convertToByteString(Block object) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutput out = new ObjectOutputStream(bos)) {
            out.writeObject(object);
            final byte[] byteArray = bos.toByteArray();
            return Base64.getEncoder().encodeToString(byteArray);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static Block convertFromByteString(String byteString) {
        final byte[] bytes = Base64.getDecoder().decode(byteString);
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes); ObjectInput in = new ObjectInputStream(bis)) {
            return (Block) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Block> read() {
        Scanner s = null;
        try {
            s = new Scanner(new File("output.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<Block> list = new ArrayList<>();
        while (true) {
            assert s != null;
            if (!s.hasNext()) break;
            list.add(convertFromByteString(s.next()));
        }
        s.close();
        return list;
    }

    public static void writer(List<Block> blockChain) {
        File file = new File("output.txt");
        FileOutputStream fo = null;
        try {
            fo = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert fo != null;
        PrintWriter pw = new PrintWriter(fo);
        for (Block elem : blockChain) {
            pw.println(convertToByteString(elem));
        }
        pw.close();
        try {
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
