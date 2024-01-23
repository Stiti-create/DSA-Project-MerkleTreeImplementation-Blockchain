import Includes.*;
import java.util.*;
import java.lang.Math;

public class TreeNode{
	public TreeNode parent;
	public TreeNode left;
	public TreeNode right;
	public String val;
	public boolean isLeaf;
	public int numberLeaves;
	public String maxleafval;
	public String minleafval;
	public int balanceFactor;


    public int height(TreeNode tn){
		int h;
		if (tn==null){
			h= 0;
			}
		if (tn.left==null && tn.right==null){
			h = 1;
			}
		else {
			if (tn.left==null){
				h = 1+height(tn.right);
				}
			if (tn.right==null){
				h = 1+height(tn.left);
				}
			else {
				h = 1+Math.max(height(tn.left), height(tn.right));
				}
			}
		return h;
		}


	public TreeNode SingleLeftRotation(){
		CRF o = new CRF(64);
				TreeNode ll = new TreeNode();

				TreeNode l = new TreeNode();
				ll.parent=null;
				if(this.parent!=null){
				    if(this.parent.right.val == this.val){
				    	this.parent.right = ll;
				    	}
				    else{
				    	this.parent.left = ll;
				    	}
				    ll.parent = this.parent;
			        }
				ll.right = this.right.right;
				ll.left = l;
		        ll.val = o.Fn(ll.left.val+"#"+ll.right.val);
				ll.isLeaf =  false;

		        l.left=this.left;
				l.right=this.right.left;
				l.val = o.Fn(l.left.val+"#"+l.right.val);
				if (l.right == null){
					l.maxleafval = l.val;
					}
				else {
					l.maxleafval = l.right.maxleafval;
					}
		        if (l.left == null){
					l.minleafval = l.val;
					}
				else{
					l.minleafval = l.left.minleafval;
					}

				ll.maxleafval=ll.right.maxleafval;
				ll.minleafval=l.minleafval;

				l.parent=ll;

		        l.isLeaf = false;

		        if (l.left==null && l.right == null){
					l.isLeaf = true;

					}
				l.numberLeaves = this.numberLeaves-this.right.right.numberLeaves;



				l.balanceFactor = height(l.left)-height(l.right);

		        ll.numberLeaves = this.numberLeaves;
		        //ll.balanceFactor= height(ll.left)-height(ll.right);

		        this.right.right.parent = ll;
		        this.right.left.parent = l;
		        this.left.parent = l;

		return ll;
	}

	public TreeNode SingleRightRotation(){
		//Implement your code here

        CRF o = new CRF(64);
		TreeNode rr = new TreeNode();

		TreeNode r = new TreeNode();
		rr.parent=null;
		if(this.parent!=null){
		    if(this.parent.right.val == this.val){
		    	this.parent.right = rr;
		    	}
		    else{
		    	this.parent.left = rr;
		    	}
		    rr.parent = this.parent;
	        }
		rr.left = this.left.left;
		rr.right = r;
        rr.val = o.Fn(rr.left.val+"#"+rr.right.val);
		rr.isLeaf =  false;

        r.left=this.left.right;
		r.right=this.right;
		r.val = o.Fn(r.left.val+"#"+r.right.val);
		if (r.right == null){
			r.maxleafval = r.val;
			}
		else {
			r.maxleafval = r.right.maxleafval;
			}
        if (r.left == null){
			r.minleafval = r.val;
			}
		else{
			r.minleafval = r.left.minleafval;
			}

		rr.maxleafval=r.maxleafval;
		rr.minleafval=rr.left.minleafval;

		r.parent=rr;

        r.isLeaf = false;

        if (r.left==null && r.right == null){
			r.isLeaf = true;

			}
		r.numberLeaves = this.numberLeaves-this.left.left.numberLeaves;



		r.balanceFactor = height(r.left)-height(r.right);

        rr.numberLeaves = this.numberLeaves;
      //  rr.balanceFactor= height(rr.left)-height(rr.right);

        this.left.left.parent = rr;
        this.left.right.parent = r;
        this.right.parent = r;

		return rr;
	}

	public TreeNode DoubleLeftRightRotation(){

		CRF o2 = new CRF(64);

		TreeNode lr = new TreeNode();
		TreeNode lrl = new TreeNode();
		TreeNode lrr = new TreeNode();
        if (this.parent!=null){
			if (this.parent.left.val == this.val){
				this.parent.left = lr;
				}
			else {
			 	this.parent.right = lr;
		    }
			}

		lr.parent = this.parent;
        lr.left = lrl;
		lr.right = lrr;
		lrl.parent = lr;
		lrr.parent = lr;

		//lrl define

	    lrl.left = this.left.left;
	    lrl.right = this.left.right.left;
	    lrl.val = o2.Fn(this.left.left.val + "#" + this.left.right.left.val);
	    lrl.isLeaf = false;
	    if (lrl.left == null && lrl.right == null){
			lrl.isLeaf = true;
			}
		lrl.numberLeaves = lrl.left.numberLeaves + lrl.right.numberLeaves;
		if (lrl.right == null){
					lrl.maxleafval = lrl.val;
					}
				else {
					lrl.maxleafval = lrl.right.maxleafval;
					}
		        if (lrl.left == null){
					lrl.minleafval = lrl.val;
					}
				else{
					lrl.minleafval = lrl.left.minleafval;
			}
		lrl.balanceFactor = height(lrl.left) - height(lrl.right);

		//lrr define

		lrr.left = this.left.right.right;
		lrr.right = this.right;
		lrr.val = o2.Fn(lrr.left.val + "#" + lrr.right.val);
	    lrr.isLeaf = false;
		if (lrr.left == null && lrr.right == null){
			lrr.isLeaf = true;
			}
		lrr.numberLeaves = lrr.left.numberLeaves + lrr.right.numberLeaves;
		if (lrr.right == null){
					lrr.maxleafval = lrr.val;
					}
				else {
					lrr.maxleafval = lrr.right.maxleafval;
					}
		        if (lrr.left == null){
					lrr.minleafval = lrr.val;
					}
				else{
					lrr.minleafval = lrr.left.minleafval;
			}
		lrr.balanceFactor = height(lrr.left) - height(lrr.right);


		lr.numberLeaves = lrl.numberLeaves + lrr.numberLeaves;
		//lr.balanceFactor = height(lrl) - height(lrr);
		lr.minleafval = lrl.minleafval;
		lr.maxleafval = lrr.maxleafval;
		lr.val = o2.Fn(lrl.val+"#"+lrr.val);
		lr.isLeaf = false;

		this.left.left.parent = lrl;
		this.left.right.left.parent = lrl;
		this.left.right.right.parent = lrr;
		this.right.parent = lrr;



		return lr;
	}

	public TreeNode DoubleRightLeftRotation(){

		CRF o3 = new CRF(64);

		TreeNode rl = new TreeNode();
		TreeNode rll = new TreeNode();
		TreeNode rlr = new TreeNode();
		rl.parent= null;
        if (this.parent != null){
			if (this.parent.left.val == this.val){
				this.parent.left = rl;
				}
			else {
				this.parent.right = rl;
			    }
			rl.parent = this.parent;
			}

		rlr.parent = rl;
		rll.parent = rl;

		rll.left = this.left;
		rll.right = this.right.left.left;
		rll.val = o3.Fn(rll.left.val + "#" + rll.right.val);
		rll.isLeaf = false;
	    if (rll.left == null && rll.right == null){
	        rll.isLeaf = true;
		    }
		rll.numberLeaves = rll.left.numberLeaves + rll.right.numberLeaves;
		if (rll.right == null){
			rll.maxleafval = rll.val;
			}
		else {
			rll.maxleafval = rll.right.maxleafval;
			}
		if (rll.left == null){
			rll.minleafval = rll.val;
			}
		else{
			rll.minleafval = rll.left.minleafval;
			}
		rll.balanceFactor = height(rll.left) - height(rll.right);

		rlr.left = this.right.left.right;
		rlr.right = this.right.right;
		rlr.val = o3.Fn(rlr.left.val + "#" + rlr.right.val);
		rlr.isLeaf = false;
		if (rlr.left == null && rlr.right == null){
			rlr.isLeaf = true;
		    }
		rlr.numberLeaves = rlr.left.numberLeaves + rlr.right.numberLeaves;
		if (rlr.right == null){
			rlr.maxleafval = rlr.val;
			}
		else {
			rlr.maxleafval = rlr.right.maxleafval;
			}
        if (rlr.left == null){
			rlr.minleafval = rlr.val;
			}
		else{
		    rlr.minleafval = rlr.left.minleafval;
			}
		rlr.balanceFactor = height(rlr.left) - height(rlr.right);

		rl.left = rll;
		rl.right = rlr;
		rl.numberLeaves = rll.numberLeaves + rlr.numberLeaves;
		//rl.balanceFactor = height(rll) - height(rlr);
		rl.minleafval = rll.minleafval;
		rl.maxleafval = rlr.maxleafval;
		rl.val = o3.Fn(rll.val+"#"+rlr.val);
		rl.isLeaf = false;

	    this.left.parent = rll;
	    this.right.left.left.parent = rll;
	    this.right.left.right.parent = rlr;
	    this.right.right.parent = rlr;

		return rl;
	}
}

