package org.kaddiya.grorchestrator.guice.factory;

import com.google.inject.name.Named;
import org.kaddiya.grorchestrator.managers.interfaces.DockerRemoteInterface;
import org.kaddiya.grorchestrator.models.core.latest.Host;
import org.kaddiya.grorchestrator.models.core.latest.Instance;

public interface DockerContainerActionFactory {
    @Named("Creator")
    public DockerRemoteInterface getContainerCreator(Instance instance, Host host);

    @Named("Puller")
    public DockerRemoteInterface getImagePuller(Instance instance, Host host);

    @Named("Runner")
    public DockerRemoteInterface getContainerRunner(Instance instance, Host host);

    @Named("Killer")
    public DockerRemoteInterface getContainerKiller(Instance instance, Host host);

    @Named("Remover")
    public DockerRemoteInterface getContainerRemover(Instance instance, Host host);

    @Named("Inspector")
    public DockerRemoteInterface getContainerInspector(Instance instance, Host host);




}
