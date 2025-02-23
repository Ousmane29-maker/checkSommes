# checkSommes

Le jeu CheckSommes est basé sur un plateau de nombres où le joueur doit sélectionner les cases qui correspondent aux sommes indiquées pour chaque ligne et colonne.

Fonctionnement :
  Affichage des cases :
    Vert : Non cliquée
    Gris : Non case
    Corail : Bonne case
  Modes de sélection :
    Oui : Cliquer = marquer comme solution
    Non : Cliquer = exclure de la solution
  Erreurs et vies :
    Une erreur = perte d’une vie
    Perte de toutes les vies = partie terminée
  Aide :
    Dévoile une case correcte (Gris ou Corail)
    Coûte 2 vies
  Interface :
    Plateau de jeu interactif
    Bouton Oui/Non pour changer de mode
    Bouton Aide
    Affichage des vies restantes (cœurs)
    Historique des coups avec indication spéciale pour l’aide
  Menu "Fichier" :
    Recommencer : Réinitialise la partie en cours (remet toutes les cases en vert, restaure les vies).
    Quitter : Ferme proprement l’application.
    Charger une partie : Permet d’importer un plateau depuis un fichier texte.
  Menu "Paramètres"  :
    Personnalisation des couleurs (Vert, Gris, Corail).
    Nombre maximum de vies (modifiable avant de commencer une partie).
    Taille du plateau (si on veut permettre des grilles de différentes dimensions).
Le but est de résoudre la grille avant d’épuiser ses vies.
