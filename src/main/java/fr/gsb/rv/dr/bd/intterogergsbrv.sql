
/* FONCTIONNE */
SELECT T.vis_matricule, V.vis_nom
FROM(   SELECT vis_matricule,MAX(jjmmaa) AS MaxDate
        FROM Travailler
        GROUP BY vis_matricule) derniere_date_for_each_visiteur
JOIN Travailler T ON derniere_date_for_each_visiteur.vis_matricule =T.vis_matricule
INNER JOIN Visiteur V ON V.vis_matricule=T.vis_matricule
AND derniere_date_for_each_visiteur.MaxDate = T.jjmmaa
AND T.tra_role='Délégué';


