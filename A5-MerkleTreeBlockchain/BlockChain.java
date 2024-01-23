import Includes.*;
import java.util.*;

public class BlockChain{
	public static final String start_string = "LabModule5";
	public Block firstblock;
	public Block lastblock;

	public String InsertBlock(List<Pair<String,Integer>> Documents, int inputyear){
		/*
			Implement Code here
		*/
		CRF obj = new CRF(64);
		Block inblock = new Block();
		if (this.firstblock == null) {
			inblock.previous = null;
			inblock.next = null;
			inblock.year = inputyear;
			MerkleTree mt = new MerkleTree();
			String v = mt.Build(Documents);
	        inblock.value = v+"_"+mt.rootnode.maxleafval;
			inblock.mtree = mt;
			inblock.dgst = obj.Fn(this.start_string+"#"+inblock.value);
			this.firstblock = inblock;
			this.lastblock = inblock;
			}
        else{
			inblock.previous = this.lastblock;
			this.lastblock.next = inblock;
	        inblock.next = null;
	        inblock.year = inputyear;
	        MerkleTree mt = new MerkleTree();
	        String v = mt.Build(Documents);
	        inblock.value = v+"_"+mt.rootnode.maxleafval;
	        inblock.mtree = mt;
            inblock.dgst = obj.Fn(inblock.previous.dgst+"#"+inblock.value);
            this.lastblock = inblock;
			}

		return this.lastblock.dgst;
	}

	public Pair<List<Pair<String,String>>, List<Pair<String,String>>> ProofofScore(int student_id, int year){
		// Implement Code here
		Block b = this.firstblock;
		if (b==null) return null;
		while(b!=null && b.year!=year){
			b = b.next;
			}

		TreeNode curr = b.mtree.rootnode;
		int n = b.mtree.numstudents;
		int h = (int)((float)Math.log(n)/(float)Math.log(2));
		int l = student_id;
		for(int x=1; x<=h; x++){
			if (l<n/2){
	     	    curr=curr.left;
			 	n = n/2;
				}
			else {
				curr=curr.right;
				l=l-n/2;
				n=n/2;
				}
			}

		List<Pair<String,String>> path = new ArrayList<Pair<String,String>>();
		while(curr.parent!=null){
            Pair<String, String> p= new Pair<String, String>();
			p.First = curr.parent.left.val;
	        p.Second = curr.parent.right.val;
            path.add(p);
            curr = curr.parent;
			}
		Pair<String, String> p1= new Pair<String, String>();
		p1.First = curr.val;
		p1.Second = null;
		path.add(p1);

		List<Pair<String,String>> nodes = new ArrayList<Pair<String,String>>();

        int c = 0;
		while(b!=null){
			Pair<String, String> p2 = new Pair<String, String>();
			p2.First = b.value;
			p2.Second = b.dgst;
			nodes.add(p2);
			c++;
			b = b.next;
			}
		Pair<List<Pair<String,String>>, List<Pair<String,String>>> proof = new Pair<List<Pair<String,String>>, List<Pair<String,String>>>();
		proof.First = path;
		proof.Second = nodes;

		return proof;
	}
}
