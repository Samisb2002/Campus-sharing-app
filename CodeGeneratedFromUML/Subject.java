// Interface Subject
public interface Subject {
    // Méthode pour enregistrer un observateur
    // Un observateur est un objet qui souhaite être informé des changements de l'état de l'objet Subject
    void registerObserver(Observer observer);

    // Méthode pour retirer un observateur
    // Un observateur peut se désabonner afin de ne plus recevoir d'informations sur l'état de l'objet Subject
    void removeObserver(Observer observer);

    // Méthode pour notifier tous les observateurs
    // Cette méthode est utilisée pour informer tous les observateurs inscrits que l'état de l'objet Subject a changé
    void notifyObservers();
}
