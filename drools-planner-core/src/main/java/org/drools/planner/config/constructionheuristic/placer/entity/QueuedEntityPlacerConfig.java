/*
 * Copyright 2012 JBoss Inc
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

package org.drools.planner.config.constructionheuristic.placer.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import org.drools.planner.config.EnvironmentMode;
import org.drools.planner.config.constructionheuristic.placer.value.ValuePlacerConfig;
import org.drools.planner.config.heuristic.selector.common.SelectionOrder;
import org.drools.planner.config.heuristic.selector.entity.EntitySelectorConfig;
import org.drools.planner.config.util.ConfigUtils;
import org.drools.planner.core.constructionheuristic.placer.entity.QueuedEntityPlacer;
import org.drools.planner.core.constructionheuristic.placer.value.ValuePlacer;
import org.drools.planner.core.domain.solution.SolutionDescriptor;
import org.drools.planner.core.heuristic.selector.common.SelectionCacheType;
import org.drools.planner.core.heuristic.selector.entity.EntitySelector;
import org.drools.planner.core.termination.Termination;

@XStreamAlias("queuedEntityPlacer")
public class QueuedEntityPlacerConfig extends EntityPlacerConfig {

    @XStreamAlias("entitySelector")
    protected EntitySelectorConfig entitySelectorConfig = new EntitySelectorConfig();
    @XStreamImplicit(itemFieldName = "valuePlacer")
    protected List<ValuePlacerConfig> valuePlacerConfigList = Arrays.asList(new ValuePlacerConfig());

    public EntitySelectorConfig getEntitySelectorConfig() {
        return entitySelectorConfig;
    }

    public void setEntitySelectorConfig(EntitySelectorConfig entitySelectorConfig) {
        this.entitySelectorConfig = entitySelectorConfig;
    }

    public List<ValuePlacerConfig> getValuePlacerConfigList() {
        return valuePlacerConfigList;
    }

    public void setValuePlacerConfigList(List<ValuePlacerConfig> valuePlacerConfigList) {
        this.valuePlacerConfigList = valuePlacerConfigList;
    }

    // ************************************************************************
    // Builder methods
    // ************************************************************************

    public QueuedEntityPlacer buildEntityPlacer(EnvironmentMode environmentMode,
            SolutionDescriptor solutionDescriptor, Termination phaseTermination) {
        // TODO filter out initialized entities
        EntitySelector entitySelector = entitySelectorConfig.buildEntitySelector(environmentMode,
                solutionDescriptor, SelectionCacheType.JUST_IN_TIME, SelectionOrder.ORIGINAL); // TODO fix selection order
        List<ValuePlacer> valuePlacerList = new ArrayList<ValuePlacer>(valuePlacerConfigList.size());
        for (ValuePlacerConfig valuePlacerConfig : valuePlacerConfigList) {
            valuePlacerList.add(valuePlacerConfig.buildValuePlacer(environmentMode,
                    solutionDescriptor, phaseTermination, entitySelector.getEntityDescriptor()));
        }
        return new QueuedEntityPlacer(entitySelector, valuePlacerList);
    }

    public void inherit(QueuedEntityPlacerConfig inheritedConfig) {
        super.inherit(inheritedConfig);
        if (entitySelectorConfig == null) {
            entitySelectorConfig = inheritedConfig.getEntitySelectorConfig();
        } else if (inheritedConfig.getEntitySelectorConfig() != null) {
            entitySelectorConfig.inherit(inheritedConfig.getEntitySelectorConfig());
        }
        valuePlacerConfigList = ConfigUtils.inheritMergeableListProperty(
                valuePlacerConfigList, inheritedConfig.getValuePlacerConfigList());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + entitySelectorConfig + ", " + valuePlacerConfigList + ")";
    }

}
