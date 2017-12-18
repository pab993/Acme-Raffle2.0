
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Folder;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Integer> {

	@Query("select f from Folder f where f.actor.id=?1 and f.name=?2")
	Folder findActorAndFolder(int idActor, String nameFolder);

	@Query("select f from Folder f where f.actor.id=?1")
	Collection<Folder> findActor(int idActor);

	//El número medio de mensajes en las carpetas del sistema.
	@Query("select avg(f.messages.size) from Folder f")
	Double getAvgForMessagesPerFolder();

}
