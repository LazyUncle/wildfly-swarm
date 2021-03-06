/**
 * Copyright 2015-2017 Red Hat, Inc, and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wildfly.swarm.tools;

import java.util.Collection;

/**
 * @author Heiko Braun
 * @author Ken Finnigan
 * @since 24/10/2016
 */
public class DefaultArtifactResolver implements ArtifactResolver {

    public DefaultArtifactResolver(ArtifactResolvingHelper resolver) {
        this.resolver = resolver;
    }

    public ArtifactSpec resolveArtifact(ArtifactSpec spec) throws Exception {
        if (spec.file == null) {
            ArtifactSpec newArtifact = this.resolver.resolve(spec);

            if (newArtifact == null) {
                throw new BuildException("Unable to resolve artifact: " + spec);
            }

            spec.file = newArtifact.file;
        }

        return spec;
    }

    public Collection<ArtifactSpec> resolveAllArtifactsTransitively(Collection<ArtifactSpec> specs, boolean defaultExcludes) throws Exception {
        return this.resolver.resolveAll(specs, true, defaultExcludes);
    }

    public Collection<ArtifactSpec> resolveAllArtifactsNonTransitively(Collection<ArtifactSpec> specs) throws Exception {
        return this.resolver.resolveAll(specs, false, false);
    }

    private ArtifactResolvingHelper resolver;
}
