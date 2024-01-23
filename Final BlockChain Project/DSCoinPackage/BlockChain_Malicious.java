package DSCoinPackage;

import HelperClasses.CRF;
import HelperClasses.MerkleTree;

public class BlockChain_Malicious {

  public int tr_count;
  public static final String start_string = "DSCoin";
  public TransactionBlock[] lastBlocksList;

  public static boolean checkTransactionBlock (TransactionBlock tB) {
	  CRF c = new CRF(64);
	  boolean bool = true;
	  if (tB.previous==null){
         if(tB.dgst.substring(0,4).compareTo("0000")!=0 || tB.dgst!=c.Fn(start_string+"#"+tB.trsummary+"#"+tB.nonce)) return false;
	     }
	  else {
		  if(tB.dgst.substring(0,4).compareTo("0000")!=0 || tB.dgst!=c.Fn(tB.previous.dgst+"#"+tB.trsummary+"#"+tB.nonce)) return false;
		  }
	  MerkleTree mt = new MerkleTree();

	  String sum = mt.Build(tB.trarray);
	  if (sum.compareTo(tB.trsummary)==0) return false;
	  for(int i=0; i<tB.tr_count; i++){
		  if(!(tB.checkTransaction(tB.trarray[i]))) return false;
		  }

    return bool;
  }

  public TransactionBlock FindLongestValidChain () {
	TransactionBlock[] array = new TransactionBlock[100];
	for(int j=0; j<100; j++){
		array[j]=null;
		}
	int count = 0;
	int max = 0;
	if (this.lastBlocksList.length==0) return null;
	TransactionBlock ret = null;
	for(int k=0; k<this.lastBlocksList.length; k++){
		TransactionBlock tb = lastBlocksList[k];

		while(checkTransactionBlock(tb) && tb!=null){
			count++;
			tb=tb.previous;
			}

		if (count>=max) {
			tb = lastBlocksList[k];
			ret = tb;
			int h=0;
			while(tb!=null){
				array[h]=tb;
				tb=tb.previous;
				h++;
			    }
			}
		}
    return ret ;
  }

  public void InsertBlock_Malicious (TransactionBlock newBlock) {
	  TransactionBlock lastBlock = this.FindLongestValidChain();
	  CRF o = new CRF(64);

      int start = 1000000001;
      String non = o.Fn(lastBlock.dgst+"#"+newBlock.trsummary+"#"+Integer.toString(start));
	  while (non.substring(0,4).compareTo("0000")!=0){
	  	start ++;
	  	non = o.Fn(lastBlock.dgst+"#"+newBlock.trsummary+"#"+Integer.toString(start));
		}
	  newBlock.nonce = non;
	  newBlock.dgst= o.Fn(lastBlock.dgst+"#"+ newBlock.trsummary+"#"+newBlock.nonce);
      newBlock.previous = lastBlock;
      int l=this.lastBlocksList.length;
      if (checkTransactionBlock(newBlock)){
          for (int u=0; u<l; u++){
		     if (this.lastBlocksList[u].trsummary.compareTo(lastBlock.trsummary)==0) this.lastBlocksList[u]=newBlock;
		     }
          }

  }
}
