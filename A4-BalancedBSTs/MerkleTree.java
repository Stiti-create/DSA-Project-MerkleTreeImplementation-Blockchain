import Includes.*;
import java.util.*;
import java.lang.Math;

public class MerkleTree{
	//check TreeNode.java for more details
	public TreeNode rootnode;
	public int numdocs;


	public String InsertDocument(String document){
		CRF obj = new CRF(64);
		String ret;

		if (this.numdocs == 0){

			//adding first node
			TreeNode tn = new TreeNode();
			tn.val = document;
			tn.parent = null;
			tn.left = null;
			tn.right = null;
			tn.isLeaf = true;
			tn.minleafval = tn.val;
			tn.maxleafval = tn.val;
			tn.balanceFactor = 0;
			tn.numberLeaves = 1;
			this.rootnode = tn;
			ret = document;

			}
		else{
			if (this.numdocs == 1){
				//adding 2nd node
				int c = document.compareTo(this.rootnode.val);
				if (c>0){
					TreeNode root = new TreeNode();
					TreeNode rght = new TreeNode();
					TreeNode lft = this.rootnode;
					root.left = lft;
					root.right = rght;
					lft.parent = root;
					rght.parent = root;
					root.parent = null;
					rght.left = null;
					rght.right = null;
					rght.val = document;
					rght.minleafval = rght.val;
					rght.maxleafval = rght.val;
					rght.isLeaf = true;
					rght.numberLeaves = 1;
					rght.balanceFactor = 0;

					root.maxleafval = rght.val;
					root.minleafval = lft.val;
					root.numberLeaves = 2;
					root.isLeaf = false;
					root.balanceFactor = 0;
					root.val = obj.Fn(root.left.val+"#"+root.right.val);
                    this.rootnode = root;
                    ret = root.val;

					}
				else {
                    TreeNode root = new TreeNode();
                    TreeNode rght = this.rootnode;
                    TreeNode lft = new TreeNode();
                    root.left = lft;
					root.right = rght;
					lft.parent = root;
					rght.parent = root;
					root.parent = null;
					rght.left = null;
					rght.right = null;
					lft.val = document;
					lft.minleafval = lft.val;
				    lft.maxleafval = lft.val;
					lft.isLeaf = true;
					lft.numberLeaves = 1;
					lft.balanceFactor = 0;
					root.maxleafval = rght.val;
					root.minleafval = lft.val;
					root.numberLeaves = 2;
					root.isLeaf = false;
					root.balanceFactor = 0;
					root.val = obj.Fn(root.left.val+"#"+root.right.val);
				    this.rootnode = root;
                    ret = root.val;

					}
				}
			else {
				//adding 3rd node
				TreeNode now = new TreeNode();
				TreeNode curr = new TreeNode();
				curr = this.rootnode;
				if ((this.rootnode.minleafval).compareTo(document)>0){

					while (curr.isLeaf==false){
						curr = curr.left;
						}
					TreeNode tn1 = new TreeNode();
                    tn1.parent= curr.parent;
                    tn1.right = curr;

					curr.parent.left = tn1;


					tn1.isLeaf = false;
					TreeNode nl = new TreeNode();
					tn1.left = nl;
					nl.val = document;
					nl.isLeaf = true;
					nl.parent = tn1;
					nl.left = null;
					nl.right = null;
					nl.minleafval = nl.val;
					nl.maxleafval = nl.val;
					nl.numberLeaves = 1;
					nl.balanceFactor = 0;
					tn1.numberLeaves = 2;
					tn1.balanceFactor = 0;
					tn1.minleafval = tn1.left.val;
					tn1.maxleafval = tn1.right.val;
					tn1.val = obj.Fn(tn1.left.val+"#"+tn1.right.val);


					curr.parent = tn1;
                    now = tn1;
					}

				else{

				    while (!curr.isLeaf){
				        if(curr.right.minleafval.compareTo(document)>0){
					        curr = curr.left;
					    	}
					    else{
							curr = curr.right;
					    	}
					    }

					TreeNode tn1 = new TreeNode();
					TreeNode tn2 = new TreeNode();
					tn2.val = document;
                    if (curr.parent.left.val == curr.val){
						curr.parent.left = tn1;
						}
					else {
						curr.parent.right = tn1;
						}
					tn1.parent = curr.parent;

					tn1.left = curr;
					tn1.right = tn2;
					tn1.left.parent = tn1;
					tn1.balanceFactor = 0;
					tn1.numberLeaves = 2;

					tn1.isLeaf = false;
					tn2.left = null;
					tn2.right = null;
					tn2.parent = tn1;
					tn2.isLeaf= true;
					tn2.minleafval = tn2.val;
					tn2.maxleafval = tn2.val;
					tn2.balanceFactor = 0;
					tn2.numberLeaves = 1;

					tn1.minleafval = tn1.left.val;
					tn1.maxleafval = tn1.right.val;
					tn1.val = obj.Fn(tn1.left.val+"#"+tn1.right.val);
                    now = tn1;
			        }

                boolean counter = true;
                if (now.parent == null){
					now.val = obj.Fn(now.left.val+"#"+now.right.val);
					ret = now.val;
					}
				else {
					while(now.parent!=null){
						if (counter){
							if (now.parent.left.val == now.val) now.parent.balanceFactor++;
							else now.parent.balanceFactor--;
							}
						if (now.parent.balanceFactor>1 || now.parent.balanceFactor<-1 ){
							counter = false;
							if (now.parent.left.val == now.val){
								if (now.right.minleafval.compareTo(document)>0){
									now.parent = now.parent.SingleRightRotation();
									//right right
									now.parent.balanceFactor--;
								    }
								else {
									now.parent = now.parent.DoubleLeftRightRotation();
									//left right
									now.parent.balanceFactor--;
									}
								}
							else{
								if (now.right.minleafval.compareTo(document)>0){
									now.parent = now.parent.DoubleRightLeftRotation();
									//right left
									now.parent.balanceFactor++;
								    }
								else {
									now.parent=now.parent.SingleLeftRotation();
									//left left
									now.parent.balanceFactor++;
									}
								}
							}
						now.parent.val = obj.Fn(now.parent.left.val + "#" + now.parent.right.val);
						now.parent.minleafval = now.parent.left.minleafval;
						now.parent.maxleafval = now.parent.right.maxleafval;
						now.parent.numberLeaves++;
						now = now.parent;
						}
					ret = now.val;
					}
			   }

			}

        this.numdocs++;
		return ret;
	}

	public String DeleteDocument(String document){
		//Implement your code here
		return "";
	}
}


