/* 
 * $Header: /home/jerenkrantz/tmp/commons/commons-convert/cvs/home/cvs/jakarta-commons-sandbox//functor/src/java/org/apache/commons/functor/core/collection/FilteredIterator.java,v 1.2 2003/11/25 19:06:42 rwaldhoff Exp $
 * ====================================================================
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2003 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "The Jakarta Project", "Commons", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived 
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    nor may "Apache" appear in their name, without prior written
 *    permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */
package org.apache.commons.functor.core.collection;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.apache.commons.functor.UnaryPredicate;

/**
 * @version $Revision: 1.2 $ $Date: 2003/11/25 19:06:42 $
 * @author Rodney Waldhoff
 */
public final class FilteredIterator implements Iterator {

    // constructor
    // ------------------------------------------------------------------------
    
    public FilteredIterator(Iterator iterator, UnaryPredicate predicate) {
        if(null == iterator || null == predicate) {
            throw new NullPointerException();
        } else {
            this.predicate = predicate;
            this.iterator = iterator;
        }
    }
    
    // iterator methods
    // ------------------------------------------------------------------------
    
    /**
     * @see java.util.Iterator#hasNext()
     */
    public boolean hasNext() {
        if(nextSet) {
            return true;
        } else {
            return setNext();
        }
    }

    /**
     * @see java.util.Iterator#next()
     */
    public Object next() {
        if(hasNext()) {            
            return returnNext();
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * @see java.util.Iterator#remove()
     */
    public void remove() {
        if(canRemove) {
            canRemove = false;
            iterator.remove();
        } else {
            throw new IllegalStateException();
        }
    }
    

    public boolean equals(Object obj) {
        if(obj instanceof FilteredIterator) {
            FilteredIterator that = (FilteredIterator)obj;
            return predicate.equals(that.predicate) && iterator.equals(that.iterator);  
        } else {
            return false;
        }
    }

    public int hashCode() {
        int hash = "FilteredIterator".hashCode();
        hash <<= 2;
        hash ^= predicate.hashCode();
        hash <<= 2;
        hash ^= iterator.hashCode();
        return hash;
    }

    public String toString() {
        return "FilteredIterator<" + iterator + "," + predicate + ">";
    }
    
    // class methods
    // ------------------------------------------------------------------------
    
    public static Iterator filter(Iterator iter, UnaryPredicate pred) {
        return null == pred ? iter : (null == iter ? null : new FilteredIterator(iter,pred));
    }
 
    // private
    // ------------------------------------------------------------------------
    
    private boolean setNext() {
        while(iterator.hasNext()) {
            canRemove = false;
            Object obj = iterator.next();
            if(predicate.test(obj)) {
                next = obj;
                nextSet = true;
                return true;
            }
        }
        next = null;
        nextSet = false;
        return false;
    }
 
    private Object returnNext() {
        Object temp = next;
        canRemove = true;
        next = null;
        nextSet = false;
        return temp;
    }
 
    // attributes
    // ------------------------------------------------------------------------
    
    private UnaryPredicate predicate = null;
    private Iterator iterator = null;
    private Object next = null;
    private boolean nextSet = false;
    private boolean canRemove = false;
    

}
