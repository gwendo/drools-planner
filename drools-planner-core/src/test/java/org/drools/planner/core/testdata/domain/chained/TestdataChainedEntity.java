package org.drools.planner.core.testdata.domain.chained;

import org.drools.planner.api.domain.entity.PlanningEntity;
import org.drools.planner.api.domain.value.ValueRange;
import org.drools.planner.api.domain.value.ValueRangeType;
import org.drools.planner.api.domain.value.ValueRanges;
import org.drools.planner.api.domain.variable.PlanningVariable;
import org.drools.planner.core.domain.entity.PlanningEntityDescriptor;
import org.drools.planner.core.domain.solution.SolutionDescriptor;
import org.drools.planner.core.testdata.domain.TestdataObject;

import static org.mockito.Mockito.*;

@PlanningEntity
public class TestdataChainedEntity extends TestdataObject implements TestdataChainedObject {

    public static PlanningEntityDescriptor buildEntityDescriptor() {
        SolutionDescriptor solutionDescriptor = TestdataChainedSolution.buildSolutionDescriptor();
        return solutionDescriptor.getPlanningEntityDescriptor(TestdataChainedEntity.class);
    }

    private TestdataChainedObject chainedObject;

    public TestdataChainedEntity() {
    }

    public TestdataChainedEntity(String code) {
        super(code);
    }

    public TestdataChainedEntity(String code, TestdataChainedObject chainedObject) {
        this(code);
        this.chainedObject = chainedObject;
    }

    @PlanningVariable(chained = true)
    @ValueRanges({
            @ValueRange(type = ValueRangeType.FROM_SOLUTION_PROPERTY, solutionProperty = "chainedAnchorList"),
            @ValueRange(type = ValueRangeType.FROM_SOLUTION_PROPERTY, solutionProperty = "chainedEntityList",
                    excludeUninitializedPlanningEntity = true)})
    public TestdataChainedObject getChainedObject() {
        return chainedObject;
    }

    public void setChainedObject(TestdataChainedObject chainedObject) {
        this.chainedObject = chainedObject;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

}
