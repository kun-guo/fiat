/*
 * Copyright 2020 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.netflix.spinnaker.fiat.roles.coding;

import com.netflix.spinnaker.fiat.config.CodingConfig;
import com.netflix.spinnaker.fiat.model.resources.Role;
import com.netflix.spinnaker.fiat.permissions.ExternalUser;
import com.netflix.spinnaker.fiat.roles.UserRolesProvider;
import java.util.*;
import java.util.stream.Collectors;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(value = "auth.group-membership.service", havingValue = "coding")
public class CodingUserRolesProvider implements UserRolesProvider {

  @Autowired @Setter private CodingConfig.ConfigProps configProps;

  @Override
  public List<Role> loadRoles(ExternalUser user) {
    log.info(" ================= coding loadRoles {}", configProps.getEndpoint());
    String userId = user.getId();

    log.info("coding loadRoles for user " + userId);

    return new ArrayList<>();
  }

  @Override
  public Map<String, Collection<Role>> multiLoadRoles(Collection<ExternalUser> users) {
    // ExternalUser is used here as a simple data type to hold the username/roles combination.
    return users.stream()
        .map(u -> new ExternalUser().setId(u.getId()).setExternalRoles(loadRoles(u)))
        .collect(Collectors.toMap(ExternalUser::getId, ExternalUser::getExternalRoles));
  }
}