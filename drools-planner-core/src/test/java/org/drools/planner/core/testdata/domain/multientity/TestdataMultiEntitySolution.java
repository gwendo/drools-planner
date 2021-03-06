/*
 * Copyright 2013 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.planner.core.testdata.domain.multientity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.drools.planner.api.domain.solution.PlanningEntityCollectionProperty;
import org.drools.planner.api.domain.solution.PlanningSolution;
import org.drools.planner.core.domain.solution.SolutionDescriptor;
import org.drools.planner.core.score.buildin.simple.SimpleScore;
import org.drools.planner.core.solution.Solution;
import org.drools.planner.core.testdata.domain.TestdataObject;
import org.drools.planner.core.testdata.domain.TestdataUtil;
import org.drools.planner.core.testdata.domain.TestdataValue;

@PlanningSolution
public class TestdataMultiEntitySolution extends TestdataObject implements Solution<SimpleScore> {

    public static SolutionDescriptor buildSolutionDescriptor() {
        return TestdataUtil.buildSolutionDescriptor(TestdataMultiEntitySolution.class,
                TestdataLeadEntity.class, TestdataHerdEntity.class);
    }

    private List<TestdataValue> valueList;
    private List<TestdataLeadEntity> leadEntityList;
    private List<TestdataHerdEntity> herdEntityList;

    private SimpleScore score;

    public TestdataMultiEntitySolution() {
    }

    public TestdataMultiEntitySolution(String code) {
        super(code);
    }

    public List<TestdataValue> getValueList() {
        return valueList;
    }

    public void setValueList(List<TestdataValue> valueList) {
        this.valueList = valueList;
    }

    @PlanningEntityCollectionProperty
    public List<TestdataLeadEntity> getLeadEntityList() {
        return leadEntityList;
    }

    public void setLeadEntityList(List<TestdataLeadEntity> leadEntityList) {
        this.leadEntityList = leadEntityList;
    }

    @PlanningEntityCollectionProperty
    public List<TestdataHerdEntity> getHerdEntityList() {
        return herdEntityList;
    }

    public void setHerdEntityList(List<TestdataHerdEntity> herdEntityList) {
        this.herdEntityList = herdEntityList;
    }

    public SimpleScore getScore() {
        return score;
    }

    public void setScore(SimpleScore score) {
        this.score = score;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

    public Collection<Object> getProblemFacts() {
        List<Object> problemFacts = new ArrayList<Object>(valueList.size());
        problemFacts.addAll(valueList);
        return problemFacts;
    }

}
