package phaseA;
import javax.print.attribute.standard.Sides;

import providedCode.*;


/**
 * TODO: Replace this comment with your own as appropriate.
 * AVLTree must be subclass of BinarySearchTree<E> and must use inheritance 
 * and calls to superclass methods to avoid unnecessary duplication or copying
 * of functionality.
 * 1. Create a subclass of BSTNode, perhaps named AVLNode.
 * 2. Override incCount method such that it creates AVLNode instances instead 
 *    of BSTNode instances.
 * 3. Do not "replace" the left and right fields in BSTNode with new left and 
 *    right fields in AVLNode. This will instead mask the super-class fields 
 *    (i.e., the resulting node would actually have four node fields, with 
 *    code accessing one pair or the other depending on the type of the
 *    references used to access the instance). Such masking will lead to
 *    highly perplexing and erroneous behavior. Instead, continue using the
 *    existing BSTNode left and right fields. Cast their values to AVLNode 
 *    whenever necessary in your AVLTree. Note: This may require many casts, 
 *    but that is o.k. given that our goal is to reuse methods from BinarySearchTree.
 * 4. Do not override dump method of BinarySearchTree & toString method of 
 * 	  DataCounter. They are used for grading. 
 * TODO: Develop appropriate JUnit tests for your AVLTree (TestAVLTree in 
 *    testA package).
 */
public class AVLTree<E> extends BinarySearchTree<E> {

	public AVLTree(Comparator<? super E> c) {
		super(c);
	}

	public void incCount(E data) {
		// if first node, we can just add it
		if (overallRoot == null) {
			overallRoot = new AVLNode(data);
			return;
		}

		// Place the node
		AVLNode currentNode = (AVLNode) overallRoot;
		while (true) {
			System.out.println("Loop: " + currentNode);
			// compare the new data with the data at the current node
			int cmp = comparator.compare(data, currentNode.data);
			if(cmp == 0) {            // a. Current node is a match
				currentNode.count++;
				return;
			} else if (cmp < 0) {       // b. Data goes left of current node
				if (currentNode.left == null) {
					currentNode.left = new AVLNode(data, currentNode);
					break;
				}	
				currentNode = (AVLNode) currentNode.left;
			} else {                    // c. Data goes right of current node
				if(currentNode.right == null) {
					currentNode.right = new AVLNode(data, currentNode);
					break;
				}
				currentNode = (AVLNode) currentNode.right;
			}
		}

		System.out.println("Perc");
		percHeightUp(currentNode);

		// at this stage, we have put the new node in the proper place
		// and fixed heights for everything. next we need to rotate

		System.out.println("Fix");
		while (currentNode != null) {
			Side isImbalanced = checkImbalance(currentNode);
			if (isImbalanced != Side.BALANCED) { rotate(currentNode, isImbalanced); }
			currentNode = currentNode.parent;
		}

	}

	/** private helper methods ============= **/
	private void rotate(AVLNode current, Side imbalance) {
		if (imbalance == Side.LEFT) {
			int left_height = (current.left == null) ? -1 : ((AVLNode) current.left).height;
			int right_height = (current.right == null) ? -1 : ((AVLNode) current.right).height;
			if (left_height > right_height) { rotateLeftLeft(current); }
			else { rotateLeftRight(current); }
		}
	}

	private void rotateLeftRight(AVLNode parent) {

		AVLNode parentNode = parent;
		AVLNode current = (AVLNode) parentNode.left;
		AVLNode moveToTop = (AVLNode) current.right;
		AVLNode LRL = (AVLNode) moveToTop.left;
		AVLNode LRR = (AVLNode) moveToTop.right;

		// set the top
		if (parent.parent == null) {
			// it is overallRoot
			this.overallRoot = moveToTop;;
			moveToTop.parent = null;
		} else if (parent.parent.left == parent) {
			// we are the left child
			parent.parent.left = moveToTop;
			moveToTop.parent = parent.parent;
		} else {
			parent.parent.right = moveToTop;
			moveToTop.parent = parent.parent;
		}

		current.right = LRL;
		if (LRL != null) { LRL.parent = current; }

		parent.left = LRR;
		if (LRR != null) { LRR.parent = parent; }

		moveToTop.left = current;
		moveToTop.right = parent;
		current.parent = moveToTop;
		parent.parent = moveToTop;

	}

	private void rotateRightLeft(AVLNode parent) {

		AVLNode parentNode = parent;
		AVLNode current = (AVLNode) parentNode.right;
		AVLNode moveToTop = (AVLNode) current.left;
		AVLNode RLR = (AVLNode) moveToTop.right;
		AVLNode RLL = (AVLNode) moveToTop.left;

		// set the top
		if (parent.parent == null) {
			// it is overallRoot
			this.overallRoot = moveToTop;;
			moveToTop.parent = null;
		} else if (parent.parent.right == parent) {
			// we are the right child
			parent.parent.right = moveToTop;
			moveToTop.parent = parent.parent;
		} else {
			parent.parent.left = moveToTop;
			moveToTop.parent = parent.parent;
		}

		current.left = RLR;
		if (RLR != null) { RLR.parent = current; }

		parent.right = RLL;
		if (RLL != null) { RLL.parent = parent; }

		moveToTop.right = current;
		moveToTop.left = parent;
		current.parent = moveToTop;
		parent.parent = moveToTop;

	}


	private void rotateLeftLeft(AVLNode parent) {
		// the left subtree is too tall -- and the left subtree of that is the problem
		AVLNode parentNode = parent;
		AVLNode current = (AVLNode) parentNode.left;

		if (parent.parent == null) {
			// it is overallRoot
			this.overallRoot = current;
			current.parent = null;
		} else if (parent.parent.left == parent) {
			// we are the left child
			parent.parent.left = current;
			current.parent = parent.parent;
		} else {
			parent.parent.right = current;
			current.parent = parent.parent;
		}

		// now, we don't have to worry about how we are connected above
		parent.left = current.right;
		if (current.right != null) { ((AVLNode) current.right).parent = parent; }

		// now the right child of current is transnferred to parent
		current.right = parent;
		parent.parent = current;
	}

	private void rotateRightRight(AVLNode parent) {
		// the right subtree is too tall -- and the right subtree of that is the problem
		AVLNode parentNode = parent;
		AVLNode current = (AVLNode) parentNode.right;

		if (parent.parent == null) {
			// it is overallRoot
			this.overallRoot = current;
			current.parent = null;
		} else if (parent.parent.right == parent) {
			// we are the right child
			parent.parent.right = current;
			current.parent = parent.parent;
		} else {
			parent.parent.left = current;
			current.parent = parent.parent;
		}

		// now, we don't have to worry about how we are connected above
		parent.right = current.left;
		if (current.left != null) { ((AVLNode) current.left).parent = parent; }

		// now the left child of current is transnferred to parent
		current.left = parent;
		parent.parent = current;

	}

	private void percHeightUp(AVLNode current) {
		while (current != null) {
			setHeight(current);
			current = current.parent;
		}
	}
	
	private void percHeightDown(AVLNode current) {
		if (current == null) {
			return;
		}
		if (current.left != null) { percHeightDown((AVLNode) current.left); }
		if (current.right != null) { percHeightDown((AVLNode) current.right); }
		
		setHeight(current);
	}

	private void setHeight(AVLNode current) {
		int left_height = (current.left == null) ? -1 : ((AVLNode) current.left).height;
		int right_height = (current.right == null) ? -1 : ((AVLNode) current.right).height;
		current.height = 1 + Math.max(left_height, right_height);
	}

	private Side checkImbalance(AVLNode current) {
		// imbalanced if one is noticable taller than other
		int left_height = (current.left == null) ? -1 : ((AVLNode) current.left).height;
		int right_height = (current.right == null) ? -1 : ((AVLNode) current.right).height;
		if (left_height - right_height > 1) { return Side.LEFT; }
		else if (right_height - left_height > 1) { return Side.RIGHT; }
		else { return Side.BALANCED; }
	}

	/** Private classes ==================== **/
	protected class AVLNode extends BSTNode {
		public AVLNode parent;
		public int height;

		public AVLNode(E data, AVLNode parent) {
			super(data);
			this.parent = parent;
			this.height = 0;
		}

		public AVLNode(E data) {
			this(data, null);
		}
	}

	private enum Side {
		LEFT, RIGHT, BALANCED;
	}
}


