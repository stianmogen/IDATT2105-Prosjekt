import classnames from 'classnames';

// Material UI Components
import { makeStyles } from '@material-ui/core/styles';

import LOGO_URL from 'assets/img/logo.png';

const useStyles = makeStyles(() => ({
  logo: {
    margin: 'auto',
    display: 'block',
    height: '100%',
    width: '100%',
  },
}));

type LogoProps = {
  size: 'small' | 'large';
  className?: string;
};

const Logo = ({ size, className }: LogoProps) => {
  const classes = useStyles();

  return (
    <svg
      className={classnames(className, classes.logo)}
      height='400'
      id='svg2'
      version='1.1'
      viewBox={size === 'large' ? '250 435 2000 450' : '335 365 400 580'}
      width={size === 'large' ? 2000 : 400}
      xmlns='http://www.w3.org/2000/svg'>
      <defs id='defs6'>
        <clipPath clipPathUnits='userSpaceOnUse' id='clipPath18'>
          <path d='M 0,1005 H 1920 V 0 H 0 Z' id='path16' />
        </clipPath>
      </defs>
      <g id='g10' transform='matrix(1.3333333,0,0,-1.3333333,0,1340)'>
        <g id='g12'>
          <g clipPath='url(#clipPath18)' id='g14'>
            <image height='330' href={LOGO_URL} transform='translate(200.4761,340.4263)' width='330' />
            {size === 'large' && <text>Gidd</text>}
          </g>
        </g>
      </g>
    </svg>
  );
};
export default Logo;
