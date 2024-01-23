package DSCoinPackage;

import HelperClasses.MerkleTree;
import HelperClasses.CRF;

public class TransactionBlock {

  public int tr_count;
  public Transaction[] trarray;
  public TransactionBlock previous;
  public MerkleTree Tree;
  public String trsummary;
  public String nonce;
  public String dgst;

  TransactionBlock(Transaction[] t) {
	  int len = t.length;

	  this.tr_count = len;

	  Transaction[] tt = new Transaction[len];

	  for (int i = 0; i<len; i++){
	     tt[i]=t[i];
	     }

	  this.trarray = tt;

	  this.previous = null;
	  MerkleTree mtr = new MerkleTree();

	  this.trsummary = mtr.Build(this.trarray);

	  this.Tree = mtr;
	  this.Tree.numdocs = len;

      this.dgst = null;



  }

  public boolean checkTransaction (Transaction t) {
	  boolean ctr = true;
	  TransactionBlock t1 = t.coinsrc_block;
	  if (t1==null) return true;

	  for (int i=0; i<t1.tr_count; i++){
		  if (t1.trarray[i].coinID.compareTo(t.coinID)==0 && t.Source.UID.compareTo(t1.trarray[i].Destination.UID)==0){
			  ctr = false;
		      }
		  }
	  if (ctr==true){
          TransactionBlock tb = this;
          while (tb!=null && tb.trsummary != t1.trsummary ){
			  for (int i=0; i<tb.tr_count; i++){
				  if (tb.trarray[i].coinID.compareTo(t.coinID)==0 ) ctr = false;
				  }
			  tb = tb.previous;
			  }

		  }
    return ctr;
  }
}
