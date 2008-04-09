/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.functor.core.composite;

import java.util.Iterator;

import org.apache.commons.functor.UnaryPredicate;

/**
 * {@link #test Tests} <code>true</code> iff
 * at least one of its children test <code>true</code>.
 * Note that by this definition, the "or" of
 * an empty collection of predicates tests <code>false</code>.
 * <p>
 * Note that although this class implements
 * {@link java.io.Serializable Serializable}, a given instance will
 * only be truly <code>Serializable</code> if all the
 * underlying functors are.  Attempts to serialize
 * an instance whose delegates are not all
 * <code>Serializable</code> will result in an exception.
 * </p>
 * @version $Revision$ $Date$
 * @author Rodney Waldhoff
 */
public final class UnaryOr extends BaseUnaryPredicateList {

    // constructor
    // ------------------------------------------------------------------------
    /**
     * Create a new UnaryOr.
     */
    public UnaryOr() {
        super();
    }

    /**
     * Create a new UnaryOr.
     * @param p Predicate to add
     */
    public UnaryOr(UnaryPredicate p) {
        super(p);
    }

    /**
     * Create a new UnaryOr.
     * @param p Predicate to add
     * @param q Predicate to add
     */
    public UnaryOr(UnaryPredicate p, UnaryPredicate q) {
        super(p, q);
    }

    /**
     * Create a new UnaryOr.
     * @param p Predicate to add
     * @param q Predicate to add
     * @param r Predicate to add
     */
    public UnaryOr(UnaryPredicate p, UnaryPredicate q, UnaryPredicate r) {
        super(p, q, r);
    }

    // modifiers
    // ------------------------------------------------------------------------
    /**
     * Fluently add a Predicate.
     * @param p Predicate to add
     * @return this
     */
    public UnaryOr or(UnaryPredicate p) {
        super.addUnaryPredicate(p);
        return this;
    }

    // predicate interface
    // ------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public boolean test(Object a) {
        for (Iterator iter = getUnaryPredicateIterator(); iter.hasNext();) {
            if (((UnaryPredicate) iter.next()).test(a)) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public boolean equals(Object that) {
        if (that instanceof UnaryOr) {
            return equals((UnaryOr) that);
        } else {
            return false;
        }
    }

    /**
     * Learn whether another UnaryOr is equal to this.
     * @param that UnaryOr to test
     * @return boolean
     */
    public boolean equals(UnaryOr that) {
        return getUnaryPredicateListEquals(that);
    }

    /**
     * {@inheritDoc}
     */
    public int hashCode() {
        return "UnaryOr".hashCode() ^ getUnaryPredicateListHashCode();
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return "UnaryOr<" + getUnaryPredicateListToString() + ">";
    }

}
