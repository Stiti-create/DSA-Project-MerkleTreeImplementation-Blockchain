package DSCoinPackage;

import HelperClasses.CRF;

public class BlockChain_Honest {

  public int tr_count;
  public static final String start_string = "DSCoin";
  public TransactionBlock lastBlock;

  public void InsertBlock_Honest (TransactionBlock newBlock) {
	  CRF o = new CRF(64);
      int start = 1000000001;
	  if (this.lastBlock==null){
	  	  newBlock.previous = null;


		  String non = o.Fn(start_string+"#"+newBlock.trsummary+"#"+Integer.toString(start));
		  while (non.substring(0,4).compareTo("0000")!=0){
		          start ++;
		  		  non = o.Fn(start_string+"#"+newBlock.trsummary+"#"+Integer.toString(start));
	  		  }
	      newBlock.nonce = Integer.toString(start);
	       this.lastBlock = newBlock;
	      newBlock.dgst = o.Fn(start_string + "#" + newBlock.trsummary + "#"+ newBlock.nonce);

		  }
      else{

		  String non = o.Fn(this.lastBlock.dgst+"#"+newBlock.trsummary+"#"+Integer.toString(start));
		  while (non.substring(0,4).compareTo("0000")!=0){
			  start ++;
			  non = o.Fn(this.lastBlock.dgst+"#"+newBlock.trsummary+"#"+Integer.toString(start));
			 }
		  newBlock.nonce = Integer.toString(start);
		  newBlock.dgst = o.Fn(this.lastBlock.dgst+ "#" + newBlock.trsummary+ "#"+ newBlock.nonce);
          newBlock.previous = this.lastBlock;
          this.lastBlock = newBlock;
		  }

  }
}
