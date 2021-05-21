// Material UI Components
import { makeStyles } from '@material-ui/core/styles';
import { Card, CardActionArea, CardContent, CardMedia, Typography } from '@material-ui/core/';

// Project Components
import { Link } from 'react-router-dom';
import URLS from 'URLS';
import { SectionsList } from 'types/Types';

// Images
import LOGO from 'assets/img/DefaultBackground.jpg';
import classnames from 'classnames';

const useStyles = makeStyles((theme) => ({
  root: {
    marginBottom: theme.spacing(1),
  },
  fullHeight: {
    height: '100%',
  },
  media: {
    height: 90,
  },
  link: {
    textDecoration: 'none',
  },
  description: {
    overflow: 'hidden',
    '-webkit-line-clamp': 4,
    display: '-webkit-box',
    '-webkit-box-orient': 'vertical',
    whiteSpace: 'break-spaces',
  },
  cardContent: {
    display: 'grid',
    gridTemplateColumns: '1fr auto',
  },
}));

export type SectionCardProps = {
  section: SectionsList;
  fullHeight?: boolean;
};

const SectionCard = ({ section, fullHeight }: SectionCardProps) => {
  const classes = useStyles();

  return (
    <Card className={classnames(classes.root, fullHeight && classes.fullHeight)}>
      <CardActionArea component={Link} to={`${URLS.ROOMS}${section.id}/`}>
        <CardMedia className={classes.media} image={LOGO} />
        <CardContent className={classes.cardContent}>
          <div>
            <Typography component='h2' gutterBottom variant='h4'>
              {section.name}
            </Typography>
            <Typography className={classes.description} variant='h5'>
              {section.capacity}
            </Typography>
          </div>
        </CardContent>
      </CardActionArea>
    </Card>
  );
};

export default SectionCard;
